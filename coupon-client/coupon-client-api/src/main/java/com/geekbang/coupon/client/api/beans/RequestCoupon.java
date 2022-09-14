package com.geekbang.coupon.client.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 优惠券请求体
 *
 * @author wuyuexiang
 * @date 2022年09月14日 19:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCoupon {

    // 用户领券
    @NotNull
    private Long customerId;

    // 券模板ID
    @NotNull
    private Long couponTemplateId;
}
