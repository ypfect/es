package com.overstar.es.service;

import com.google.common.collect.Lists;
import com.overstar.es.constants.EnumIndexType;
import com.overstar.es.domain.ProductDocument;
import com.overstar.es.mapper.ProductDocumentMapper;
import com.overstar.es.pop.EsPop;
import com.overstar.es.utils.IndexUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private IndexUtil indexUtil;
    private List<String> oldIndies;


    @Override
    String taskType() {
        return EnumIndexType.PRODUCT_DOCUMENT.getDesc();
    }

    @Override
    Object getData() {
        List<ProductDocument> documents = new ArrayList<>();
        int star = 0;
        int size = 500;
        boolean executeFlag = true;
        while (executeFlag) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("start", star);
            map.put("size", size);
            List<ProductDocument> productDocSource = mapper.getProductDocSource(map);
            star += size;
            if (productDocSource.size() < 1) {
                executeFlag = false;
            }

            if (!CollectionUtils.isEmpty(productDocSource)) {
                documents.addAll(productDocSource);
            }
        }

        return documents;
    }


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
                Object fieldValue = field.get(document);
                if (fieldValue instanceof String) {
                    String o = (String) field.get(document);
                    if (!StringUtils.isEmpty(o)) {
                        String[] split = o.split(",");
                        if (split.length > 0) {
                            map.put(field.getName(), split);
                        }
                    }

                    if (CollectionUtils.isEmpty(map)) continue;
                    IndexRequest request = new IndexRequest();
                    request.id(String.valueOf(document.getProductId()));
                    request.index(generatorIndexName());
                    request.source(map);
                    request.type("_doc");

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
        request.mapping(esPop.getIndex().getMappings(), XContentType.JSON);

        Set<Alias> aliases = request.aliases();
        aliases.forEach(alias1 -> {
            String name = alias1.name();
            System.out.println(name);
        });
        Alias alias = new Alias(esPop.getAliasProperties().getProductAlias());
        request.alias(alias);

        try {
            //别名下的老索引名称
            oldIndies = indexUtil.getIndicesByAlias(esPop.getAliasProperties().getProductAlias());
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            if (acknowledged) {
                return true;
            }
        } catch (IOException e) {
            log.error("别名={}，indexName={},创建失败了...", alias.name(), indexName);
            e.printStackTrace();
        }

        return false;
    }





    @Override
    boolean indexing(Object tem) {
        List<IndexRequest> requests = (List<IndexRequest>) tem;
        //bulk插入
        Lists.partition(requests, 1000).forEach(indexRequests -> {
            BulkRequest bulkRequest = new BulkRequest();
            //受限于整个esClient的配置
            bulkRequest.timeout(new TimeValue(5000, TimeUnit.MILLISECONDS));
            indexRequests.forEach(bulkRequest::add);
            //bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            //异步bulk
            client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkItemResponses) {
                    log.info("bulking 【{}】 success a piece",taskType());
                }
                @Override
                public void onFailure(Exception e) {
                    log.info("bulking 【{}】 Failure a piece",taskType());
                }
            });
        });

        return true;
    }

    @Override
    boolean deleteIndex() {
        if (CollectionUtils.isEmpty(oldIndies)){
            return true;
        }

        DeleteIndexRequest delIndex = new DeleteIndexRequest();
        delIndex.indices(Arrays.toString(oldIndies.toArray()));

        oldIndies.forEach(s -> {
            try {
                client.indices().delete(new DeleteIndexRequest(s),RequestOptions.DEFAULT);
                log.info("shanchu成功+{}",s);
            } catch (IOException e) {
                log.error("shanchu失败+{}",s);
                e.printStackTrace();
            }

        });
//        try {
//            client.indices().deleteAsync(delIndex, RequestOptions.DEFAULT, new ActionListener<AcknowledgedResponse>() {
//                @Override
//                public void onResponse(AcknowledgedResponse acknowledgedResponse) {
//                    log.info("删除成功！别名=【{}】,删除索引【{}】","product",oldIndies);
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                    log.error("删除失败！别名=【{}】,删除索引【{}】","product",oldIndies);
//                }
//            });
//        }catch (Exception e){
//            log.error("删除失败！别名=【{}】,删除索引【{}】","product",oldIndies);
//        }

        return true;

    }

    public String generatorIndexName() {
        SimpleDateFormat format = new SimpleDateFormat("[yyyy-MM-dd]-HH-ss-mm");
        String format1 = format.format(new Date());
        return "product_" + format1;
    }

}
