package com.geekbang.coupon.template.controller;

import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import com.geekbang.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.geekbang.coupon.template.api.beans.TemplateSearchParams;
import com.geekbang.coupon.template.service.CouponTemplateService;
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
    public Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids) {
        log.info("getTemplateInBatch: {}", ids);
        return couponTemplateService.getTemplateInfoMap(ids);
    }

    /**
     * 搜索模板
     *
     * @param searchParams TemplateSearchParams
     * @return PagedCouponTemplateInfo
     */
    @PostMapping("/search")
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
    public void deleteTemplate(@PathVariable("id") Long id){
        log.info("Load template, id={}", id);
        couponTemplateService.deleteTemplate(id);
    }
}
