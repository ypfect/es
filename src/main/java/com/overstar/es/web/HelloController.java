package com.overstar.es.web;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.ClusterClient;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/9 16:44
 */
@RestController
@RequestMapping("/es")
public class HelloController {

    @Autowired
    private RestHighLevelClient client;


    @RequestMapping("/hel")
    public String getHealthy() throws IOException {
        MainResponse info = client.info(RequestOptions.DEFAULT);
        return info.toString();
    }
}
