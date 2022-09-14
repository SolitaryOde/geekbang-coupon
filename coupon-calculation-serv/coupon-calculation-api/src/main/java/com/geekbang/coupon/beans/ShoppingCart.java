package com.geekbang.coupon.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 购物车
 *
 * @author wuyuexiang
 * @date 2022年09月13日 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    @NotEmpty
    private List<Product> products;

    private Long couponId;

    private Long cost;

    // 目前只支持单张优惠券
    // 但是为了以后的扩展考虑，你可以添加多个优惠券的计算逻辑
    private List<CouponInfo> couponInfos;

    @NotNull
    private Long customerId;
}
