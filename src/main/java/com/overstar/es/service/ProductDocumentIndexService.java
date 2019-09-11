package com.overstar.es.service;

import com.overstar.es.constants.EnumIndexType;
import com.overstar.es.domain.ProductDocument;
import com.overstar.es.mapper.ProductDocumentMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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


    @Override
    String taskType() {
        return EnumIndexType.PRODUCT_DOCUMENT.getDesc();
    }

    @Override
    Object getData() {
        List<ProductDocument> documents = new ArrayList<>();
        int star=0;
        int size=0;
        boolean executeFlag=true;
        while (executeFlag){
            HashMap<String, Integer> map = new HashMap<>();
            map.put("start",star);
            map.put("size",size);
            List<ProductDocument> productDocSource = mapper.getProductDocSource(map);
            if (productDocSource.size()<1){
                executeFlag= false;
            }

            if (!CollectionUtils.isEmpty(productDocSource)){
                documents.addAll(productDocSource);
            }
        }

        return documents;
    }

    @Override
    Object dataPrepare(Object data) {
        List<ProductDocument> documents = (List<ProductDocument>) data;
        for (ProductDocument document : documents) {
            String productNameEn = document.getProductNameEn();


        }


        return null;
    }

    @Override
    boolean createIndex() {
        String indexName = gennerateIndexName();
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 2、设置索引的settings
        request.settings("asda", XContentType.JSON);
        request.mapping("asda",XContentType.JSON);

        return false;
    }

    @Override
    boolean indexing(Object tem) {
        return false;
    }

    @Override
    boolean deleteIndex() {
        return false;
    }

    public String gennerateIndexName(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        String format1 = format.format(new Date());
        return new StringBuilder("product_document_").append(format1).toString();
    }

}
