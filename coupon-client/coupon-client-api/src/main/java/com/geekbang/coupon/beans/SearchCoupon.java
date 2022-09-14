package com.geekbang.coupon.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 查询优惠券
 *
 * @author wuyuexiang
 * @date 2022年09月14日 19:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCoupon {

    @NotNull
    private Long customerId;

    private Long shopId;

    private Integer couponStatus;
}
