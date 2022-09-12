package com.geekbang.coupon.template.converter;

import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import com.geekbang.coupon.template.api.enums.CouponType;
import com.geekbang.coupon.template.repository.entity.CouponTemplate;

/**
 * @author wuyuexiang
 * @date 2022年09月12日 21:49
 */
public class CouponTemplateConverter {

    private CouponTemplateConverter() {}

    public static CouponTemplateInfo convertEntityToBean(CouponTemplate entity) {
        return CouponTemplateInfo.builder()
                .id(entity.getId())
                .name(entity.getName())
                .desc(entity.getDescription())
                .type(entity.getType().getCode())
                .shopId(entity.getShopId())
                .available(entity.getAvailable())
                .rule(entity.getRule())
                .build();
    }

    public static CouponTemplate convertBeanToEntity(CouponTemplateInfo bean) {
        return CouponTemplate.builder()
                .id(bean.getId())
                .name(bean.getName())
                .description(bean.getDesc())
                .type(CouponType.convert(bean.getType()))
                .available(bean.getAvailable())
                .shopId(bean.getShopId())
                .rule(bean.getRule())
                .build();
    }
}
