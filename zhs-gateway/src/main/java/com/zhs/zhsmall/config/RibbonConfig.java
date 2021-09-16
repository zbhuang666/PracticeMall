package com.zhs.zhsmall.config;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.util.Collections;

@Configuration
public class RibbonConfig {

    @Resource
    private LoadBalancerClient loadBalancer;
    @Bean
    //@LoadBalanced  // 这么做可以吗？   SmartInitializingSingleton   InitializingBean （构建bean的init方法）
    // 顺序的问题 SmartInitializingSingleton是在所有的非懒加载单例bean构建完成之后调用的
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(
                Collections.singletonList(
                        new LoadBalancerInterceptor(loadBalancer)));

        return restTemplate;
    }
}
