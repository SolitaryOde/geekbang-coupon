package com.geekgang.coupon.calculation.rule.calculator;

import com.geekgang.coupon.calculation.rule.AbstractRuleCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 随机减规则计算器
 *
 * @author wuyuexiang
 * @date 2022年09月14日 00:12
 */
@Component
@Slf4j
public class RandomReductionRuleCalculator extends AbstractRuleCalculator {

    /**
     * 金额计算具体逻辑，延迟到子类实现
     *
     * @param originTotalAmount 订单原始总金额
     * @param shopTotalAmount 店铺总金额
     * @param quota 优惠金额或者打折比例
     * @return 优惠后的订单金额
     */
    @Override
    protected Long calculateNewPrice(Long originTotalAmount, Long shopTotalAmount, Long quota) {
        long maxBenefit = Math.min(shopTotalAmount, quota);
        int reductionAmount = new Random().nextInt(Integer.parseInt(String.valueOf(maxBenefit)));
        long newCost = originTotalAmount - reductionAmount;
        log.info("RandomReductionRuleCalculator result: {}", newCost);
        return newCost;
    }
}
