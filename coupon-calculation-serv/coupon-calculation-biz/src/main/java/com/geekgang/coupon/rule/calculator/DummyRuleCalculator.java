package com.geekgang.coupon.rule.calculator;

import com.geekbang.coupon.beans.ShoppingCart;
import com.geekgang.coupon.rule.AbstractRuleCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 无算法规则
 *
 * @author wuyuexiang
 * @date 2022年09月13日 23:43
 */
@Component
@Slf4j
public class DummyRuleCalculator extends AbstractRuleCalculator {

    /**
     * 金额计算具体逻辑
     *
     * @param originTotalAmount 订单原始总金额
     * @param shopTotalAmount 店铺总金额
     * @param quota 优惠金额或者打折比例
     * @return 优惠后的订单金额
     */
    @Override
    protected Long calculateNewPrice(Long originTotalAmount, Long shopTotalAmount, Long quota) {
        log.info("使用无算法规则 DummyRuleCalculator 计算");
        return originTotalAmount;
    }

    /**
     * 计算购物车的优惠券
     *
     * @param target 目标购物车
     * @return 计算结果封装到购物车
     */
    @Override
    public ShoppingCart calculate(ShoppingCart target) {
        target.setCost(getTotalPrice(target.getProducts()));
        return target;
    }
}
