package com.overstar.es.web;

import com.overstar.es.domain.ProductDocument;
import com.overstar.es.mapper.ProductDocumentMapper;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Description  http://www.ishenping.com/ArtInfo/1992031.html
 * @Author stanley.yu
 * @Date 2019/9/9 16:44
 */
@RestController
@RequestMapping("/es")
public class HelloController {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ProductDocumentMapper mapper;


    @RequestMapping("/create/{index}")
    public String getHealthy(@PathVariable String index) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return createIndexResponse.toString();
    }


    @RequestMapping("/data")
    public String data() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("start",0);
        map.put("size",10);
        List<ProductDocument> docSource = mapper.getProductDocSource(map);
        return docSource.toString();
    }
}
