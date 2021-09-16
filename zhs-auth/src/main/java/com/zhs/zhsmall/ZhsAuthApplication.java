package com.zhs.zhsmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZhsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhsAuthApplication.class, args);
    }
}
