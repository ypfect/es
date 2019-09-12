package com.overstar.es.service;

import com.overstar.es.constants.EnumIndexType;
import com.overstar.es.domain.ProductDocument;
import com.overstar.es.mapper.ProductDocumentMapper;
import com.overstar.es.pop.EsPop;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/11 14:20
 */
@Service
@Slf4j
public class ProductDocumentIndexService extends AbstractIndexService {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ProductDocumentMapper mapper;
    @Autowired
    private EsPop esPop;


    @Override
    String taskType() {
        return EnumIndexType.PRODUCT_DOCUMENT.getDesc();
    }

    @Override
    Object getData() {
        List<ProductDocument> documents = new ArrayList<>();
        int star=0;
        int size=500;
        boolean executeFlag=true;
        while (executeFlag){
            HashMap<String, Integer> map = new HashMap<>();
            map.put("start",star);
            map.put("size",size);
            List<ProductDocument> productDocSource = mapper.getProductDocSource(map);
            star+=size;
            if (productDocSource.size()<1){
                executeFlag= false;
            }

            if (!CollectionUtils.isEmpty(productDocSource)){
                documents.addAll(productDocSource);
            }
        }

        return documents;
    }


    //看川川的
    @Override
    List<IndexRequest> dataPrepare(Object data) throws IllegalAccessException {
        List<ProductDocument> documents = (List<ProductDocument>) data;
        ArrayList<IndexRequest> requests = new ArrayList<>();
        for (ProductDocument document : documents) {
            HashMap<String, Object> map = new HashMap<>();
            Field[] fields = document.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                if (field.get(document) instanceof String) {
                    String o = (String)field.get(document);
                    if (!StringUtils.isEmpty(o)){
                        String[] split = o.split(",");
                        if (split.length>0){
                            map.put(field.getName(),split);
                        }
                    }

                    if (CollectionUtils.isEmpty(map))continue;
                    IndexRequest request = new IndexRequest();
                    request.id(String.valueOf(document.getProductId()));
                    request.index(generatorIndexName());
                    request.source(map);

                    requests.add(request);
                }

            }

        }

        return requests;
    }

    @Override
    public boolean createIndex() {
        String indexName = generatorIndexName();
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 2、设置索引的settings
        request.settings(esPop.getIndex().getSettings(), XContentType.JSON);
        request.mapping(esPop.getIndex().getMappings(),XContentType.JSON);
        Alias alias = new Alias("product");
        request.alias(alias);
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            if (acknowledged){
                return true;
            }
        } catch (IOException e) {
            log.error("别名={}，indexName={},创建失败了...",alias.name(),indexName);
            e.printStackTrace();
        }

        return false;
    }

    @Override
    boolean indexing(Object tem) {
        //bulk插入

        return false;
    }

    @Override
    boolean deleteIndex() {
        return false;
    }

    public String generatorIndexName(){
        SimpleDateFormat format = new SimpleDateFormat("[yyyy-MM-dd]HH");
        String format1 = format.format(new Date());
        return "product_document_" + format1;
    }

}
