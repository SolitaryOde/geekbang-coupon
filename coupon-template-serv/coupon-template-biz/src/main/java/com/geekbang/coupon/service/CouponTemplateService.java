package com.geekbang.coupon.service;

import com.geekbang.coupon.beans.CouponTemplateInfo;
import com.geekbang.coupon.beans.PagedCouponTemplateInfo;
import com.geekbang.coupon.beans.TemplateSearchParams;

import java.util.Collection;
import java.util.Map;

/**
 * 优惠券模版服务
 *
 * @author wuyuexiang
 * @date 2022年09月12日 21:53
 */
public interface CouponTemplateService {

    /**
     * 创建优惠券模板
     *
     * @param couponTemplateInfo 模版信息
     * @return 模版信息
     */
    CouponTemplateInfo createTemplate(CouponTemplateInfo couponTemplateInfo);

    /**
     * 复制优惠券模板
     *
     * @param templateId 模版id
     * @return 模版信息
     */
    CouponTemplateInfo cloneTemplate(Long templateId);

    /**
     * 模板查询（分页）
     *
     * @param searchParams 查询参数
     * @return 分页结果
     */
    PagedCouponTemplateInfo search(TemplateSearchParams searchParams);

    /**
     * 通过模板ID查询优惠券模板
     *
     * @param id 模版id
     * @return 模版信息
     */
    CouponTemplateInfo loadTemplateInfo(Long id);

    /**
     * 让优惠券模板无效
     *
     * @param id 模版id
     */
    void deleteTemplate(Long id);

    /**
     * 批量查询
     * Map是模板ID，key是模板详情
     *
     * @param ids 模版id
     * @return 模板详情
     */
    Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids);
}
