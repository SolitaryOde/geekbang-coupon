package com.geekbang.coupon.converter;

import com.geekbang.coupon.repository.entity.Coupon;
import com.geekbang.coupon.beans.CouponInfo;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 优惠券转换器
 *
 * @author wuyuexiang
 * @date 2022年09月14日 20:25
 */
public class CouponConverter {

    private CouponConverter() {}

    public static CouponInfo convertEntityToBean(Coupon entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return CouponInfo.builder().build();
        }
        return CouponInfo.builder()
                .id(entity.getId())
                .status(entity.getStatus().getCode())
                .templateId(entity.getTemplateId())
                .shopId(entity.getShopId())
                .customerId(entity.getCustomerId())
                .template(entity.getTemplateInfo())
                .build();
    }
}
