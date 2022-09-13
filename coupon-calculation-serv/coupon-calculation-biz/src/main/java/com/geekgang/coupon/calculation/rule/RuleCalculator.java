package com.geekgang.coupon.calculation.rule;

import com.geekbang.coupon.calculation.api.beans.ShoppingCart;

/**
 * 规则计算器
 *
 * @author wuyuexiang
 * @date 2022年09月13日 22:31
 */
public interface RuleCalculator {

    /**
     * 计算购物车的优惠券
     *
     * @param target 目标购物车
     * @return 计算结果封装到购物车
     */
    ShoppingCart calculate(ShoppingCart target);
}
