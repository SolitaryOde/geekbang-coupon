package com.geekbang.coupon.config.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Sentinel 请求来源解析器
 *
 * @author wuyuexiang
 * @date 2022年09月18日 22:02
 */
@Component
@Slf4j
public class SentinelOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        log.info("request parameter {}, header={}", request.getParameterMap(), request.getHeaderNames());
        return request.getHeader("SentinelOrigin");
    }
}
