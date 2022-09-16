package com.geekbang.coupon.config.http;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign配置
 *
 * @author wuyuexiang
 * @date 2022年09月16日 23:56
 */
@Configuration
public class OpenFeignConfiguration {

    @Bean
    Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }
}
