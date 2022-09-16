package com.geekbang.coupon.feign;

import com.geekbang.coupon.beans.CouponTemplateInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

/**
 * 优惠券模版服务接口
 *
 * @author wuyuexiang
 * @date 2022年09月16日 23:10
 */
@FeignClient(value = "coupon-template-serv", path = "/coupon/template")
public interface CouponTemplateService {

    /**
     * 读取优惠券模版
     *
     * @param id 模版id
     * @return CouponTemplateInfo
     */
    @GetMapping("/{id}")
    CouponTemplateInfo getTemplate(@PathVariable("id") Long id);

    /**
     * 批量获取
     *
     * @param ids Collection<Long>
     * @return Map<Long, CouponTemplateInfo>
     */
    @GetMapping("/batch")
    Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids);
}
