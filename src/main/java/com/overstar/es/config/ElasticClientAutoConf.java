package com.overstar.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/9 15:03
 */
@Configuration
@Slf4j
public class ElasticClientAutoConf implements FactoryBean<RestHighLevelClient> , InitializingBean, DisposableBean {

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private int esPort;

    private RestHighLevelClient client;

    @Override
    public void destroy() throws Exception {
        try {
            log.info("try to close els-client ...");
            client.close();
            log.info("close els-client successfully");
        }catch (Throwable e){
            log.error("close els-client failure");
        }
    }

    @Override
    public RestHighLevelClient  getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("create elasticsearch restHeightLevel client...");
        buildClient();
    }


    protected void buildClient()  {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(esHost,esPort)));
    }

}
