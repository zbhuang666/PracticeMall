package com.zhs.zhsmall.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RestHighLevelClientConfig {
    @Value("${es.student.rest.uris}")
    private String uris;
    @Value("${es.student.rest.connection-timeout}")
    private int connectionTimeout;
    @Value("${es.student.rest.socket-timeout}")
    private int socketTimeout;
    @Value("${es.student.rest.connection-request-timeout}")
    private int connectionRequestTimeout;

    @Bean(destroyMethod = "close", name = "client")
    public RestHighLevelClient restHighLevelClient(){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,password));
        String[] uriArr = uris.split(",");
        HttpHost[] httpHosts = new HttpHost[uriArr.length];
        for (int i = 0; i < uriArr.length; i++) {
            httpHosts[i] = HttpHost.create(uriArr[i]);
        }
        RestClientBuilder builder = RestClient.builder(httpHosts)
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(connectionTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectionRequestTimeout(connectionRequestTimeout)
                ).setHttpClientConfigCallback(f->f.setDefaultCredentialsProvider(credentialsProvider));//带密码的ES连接访问
        return new RestHighLevelClient(builder);
    }
}
