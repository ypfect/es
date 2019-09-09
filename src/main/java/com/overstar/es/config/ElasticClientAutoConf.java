package com.overstar.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.XPackClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/9 15:03
 */
@Configuration
@Slf4j
public class ElasticClientAutoConf {

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private int esPort;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient initClient(){
        log.info("host地址={}，端口={}",esHost,esPort);
        RestClientBuilder builder = RestClient.builder(new HttpHost(esHost, esPort));

        RestHighLevelClient levelClient = new RestHighLevelClient(builder);
        return levelClient;
    }
}
