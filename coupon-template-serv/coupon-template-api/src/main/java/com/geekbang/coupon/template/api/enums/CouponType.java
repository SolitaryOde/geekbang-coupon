package com.geekbang.coupon.template.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 优惠券类型
 *
 * @author wuyuexiang
 * @date 2022年09月12日 00:02
 */
@Getter
@AllArgsConstructor
public enum CouponType {

    UNKNOWN("unknown", 0),
    MONEY_OFF("满减券", 1),
    DISCOUNT("打折", 2),
    RANDOM_DISCOUNT("随机减", 3),
    LONELY_NIGHT_MONEY_OFF("晚间双倍优惠券", 4);

    private final String description;
    // 存在数据库里的最终code
    private final Integer code;

    public static CouponType convert(Integer code) {
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}