package com.geekbang.coupon.client.api.enumes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 优惠券状态
 *
 * @author wuyuexiang
 * @date 2022年09月14日 18:58
 */
@Getter
@AllArgsConstructor
public enum CouponStatus {

    UNKNOWN("未知状态", 0),
    AVAILABLE("未使用", 1),
    USED("已用", 2),
    INACTIVE("已经注销", 3);

    private final String desc;
    private final Integer code;

    public static CouponStatus convert(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
