package com.geekbang.coupon.config.sentinel;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * Sentinel Feign请求拦截器
 *
 * @author wuyuexiang
 * @date 2022年09月18日 21:55
 */
@Component
public class SentinelFeignRequestInterceptor implements RequestInterceptor {

    /**
     * 添加 Sentinel 来源
     *
     * @param requestTemplate RequestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("SentinelOrigin", "coupon-client");
    }
}
