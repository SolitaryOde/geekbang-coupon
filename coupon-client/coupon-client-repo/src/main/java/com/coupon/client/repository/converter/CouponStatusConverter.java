package com.coupon.client.repository.converter;

import com.geekbang.coupon.client.api.enumes.CouponStatus;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 优惠券状态转换器
 *
 * @author wuyuexiang
 * @date 2022年09月14日 19:20
 */
@Converter
public class CouponStatusConverter implements AttributeConverter<CouponStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponStatus couponStatus) {
        if (ObjectUtils.isEmpty(couponStatus)) {
            return CouponStatus.UNKNOWN.getCode();
        }
        return couponStatus.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer statusCode) {
        return CouponStatus.convert(statusCode);
    }
}
