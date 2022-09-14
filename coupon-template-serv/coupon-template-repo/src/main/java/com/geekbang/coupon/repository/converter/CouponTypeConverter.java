package com.geekbang.coupon.repository.converter;

import com.geekbang.coupon.enums.CouponType;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 * @author wuyuexiang
 * @date 2022年09月12日 01:08
 */
@Convert
public class CouponTypeConverter implements AttributeConverter<CouponType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponType couponCategory) {
        return couponCategory.getCode();
    }

    @Override
    public CouponType convertToEntityAttribute(Integer code) {
        return CouponType.convert(code);
    }
}
