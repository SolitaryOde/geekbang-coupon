package com.geekbang.coupon.supporter;

import com.geekbang.coupon.constant.CommonConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient请求工具
 *
 * @author wuyuexiang
 * @date 2022年09月16日 01:50
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientSupporter {

    private final WebClient.Builder webClientBuilder;

    public <T> T sendGet(String url, Class<T> responseType) {
        log.info("WebClient GET start: url={}, responseType={}", url, responseType);
        T response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType)
                .block();
        log.info("WebClient GET end: url={}, response: {}", url, response);
        return response;
    }

    public <T> T sendCanaryGet(String url, String trafficVersion, Class<T> responseType) {
        log.info("WebClient GET start: url={}, responseType={}", url, responseType);
        T response = webClientBuilder.build()
                .get()
                .uri(url)
                .header(CommonConstant.TRAFFIC_VERSION, trafficVersion)
                .retrieve()
                .bodyToMono(responseType)
                .block();
        log.info("WebClient GET end: url={}, response: {}", url, response);
        return response;
    }

    public <T> T sendPost(String url, Object requestBody, Class<T> responseType) {
        log.info("WebClient POST start: url={}, requestBody={}, responseType={}", url, requestBody, responseType);
        T response = webClientBuilder.build()
                .post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .block();
        log.info("WebClient POST end: url={}, response: {}", url, response);
        return response;
    }
}
