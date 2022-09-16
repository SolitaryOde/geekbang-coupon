package com.geekbang.coupon.config.http;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient配置
 *
 * @author wuyuexiang
 * @date 2022年09月16日 01:05
 */
@Configuration
public class WebClientConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder clientBuilder() {
        return WebClient.builder();
    }
}
