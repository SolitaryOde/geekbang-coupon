package com.geekbang.coupon.rule.calculator;

import com.geekbang.coupon.rule.AbstractRuleCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 打折规则计算器
 *
 * @author wuyuexiang
 * @date 2022年09月13日 23:33
 */
@Component
@Slf4j
public class DiscountRuleCalculator extends AbstractRuleCalculator {

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
        // 计算使用优惠券之后的价格
        Long newPrice = convertToDecimal(shopTotalAmount * (quota.doubleValue() / 100));
        log.info("DiscountRuleCalculator result: {}", newPrice);
        return newPrice;
    }
}
