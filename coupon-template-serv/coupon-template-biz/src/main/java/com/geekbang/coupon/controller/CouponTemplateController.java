package com.geekbang.coupon.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.geekbang.coupon.beans.CouponTemplateInfo;
import com.geekbang.coupon.beans.PagedCouponTemplateInfo;
import com.geekbang.coupon.beans.TemplateSearchParams;
import com.geekbang.coupon.service.CouponTemplateService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * 优惠券模版控制器
 *
 * @author wuyuexiang
 * @date 2022年09月12日 22:39
 */
@RestController
@RequestMapping("/coupon/template")
@RequiredArgsConstructor
@Slf4j
public class CouponTemplateController {

    private final CouponTemplateService couponTemplateService;

    /**
     * 创建优惠券模版
     *
     * @param couponTemplateInfo CouponTemplateInfo
     * @return CouponTemplateInfo
     */
    @PostMapping
    @SentinelResource(value = "add")
    public CouponTemplateInfo addTemplate(@Valid @RequestBody CouponTemplateInfo couponTemplateInfo) {
        log.info("Create coupon template: data={}", couponTemplateInfo);
        return couponTemplateService.createTemplate(couponTemplateInfo);
    }

    /**
     * 克隆优惠券模版
     *
     * @param id 模版id
     * @return CouponTemplateInfo
     */
    @PostMapping("/clone/{id}")
    @SentinelResource(value = "clone")
    public CouponTemplateInfo cloneTemplate(@PathVariable("id") Long id) {
        log.info("Clone coupon template: data={}", id);
        return couponTemplateService.cloneTemplate(id);
    }

    /**
     * 读取优惠券模版
     *
     * @param id 模版id
     * @return CouponTemplateInfo
     */
    @GetMapping("/{id}")
    @SentinelResource(value = "get")
    public CouponTemplateInfo getTemplate(@PathVariable("id") Long id){
        log.info("Load template, id={}", id);
        return couponTemplateService.loadTemplateInfo(id);
    }

    /**
     * 批量获取
     *
     * @param ids Collection<Long>
     * @return Map<Long, CouponTemplateInfo>
     */
    @GetMapping("/batch")
    @SentinelResource(value = "batch", blockHandler = "getTemplateInBatchBlock",
            fallback = "getTemplateInBatchFallback")
    public Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids) {
        log.info("getTemplateInBatch: {}", ids);
        if (ids.contains(222L)) {
            throw new RuntimeException("熔断测试");
        }
        return couponTemplateService.getTemplateInfoMap(ids);
    }

    /**
     * 熔断或限流方法
     *
     * @param ids Collection<Long>
     * @param e BlockException
     * @return Map<Long, CouponTemplateInfo>
     */
    public Map<Long, CouponTemplateInfo> getTemplateInBatchBlock(Collection<Long> ids, BlockException e) {
        log.info("接口被熔断或限流: {}", e.getMessage());
        e.printStackTrace();
        return Maps.newHashMap();
    }

    /**
     * 接口异常回调
     *
     * @param ids Collection<Long>
     * @return Map<Long, CouponTemplateInfo>
     */
    public Map<Long, CouponTemplateInfo> getTemplateInBatchFallback(Collection<Long> ids) {
        log.info("接口产生异常！");
        return Maps.newHashMap();
    }

    /**
     * 搜索模板
     *
     * @param searchParams TemplateSearchParams
     * @return PagedCouponTemplateInfo
     */
    @PostMapping("/search")
    @SentinelResource(value = "search")
    public PagedCouponTemplateInfo search(@Valid @RequestBody TemplateSearchParams searchParams) {
        log.info("search templates, payload={}", searchParams);
        return couponTemplateService.search(searchParams);
    }

    /**
     * 优惠券无效化
     *
     * @param id 模版id
     */
    @DeleteMapping("/{id}")
    @SentinelResource(value = "delete")
    public void deleteTemplate(@PathVariable("id") Long id){
        log.info("Load template, id={}", id);
        couponTemplateService.deleteTemplate(id);
    }
}
