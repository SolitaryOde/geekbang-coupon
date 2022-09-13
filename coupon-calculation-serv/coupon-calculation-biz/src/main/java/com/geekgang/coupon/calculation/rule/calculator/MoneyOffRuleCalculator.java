package com.geekgang.coupon.calculation.rule.calculator;

import com.geekgang.coupon.calculation.rule.AbstractRuleCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 满减规则计算器
 *
 * @author wuyuexiang
 * @date 2022年09月14日 00:06
 */
@Component
@Slf4j
public class MoneyOffRuleCalculator extends AbstractRuleCalculator {

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
        // 如果当前门店的商品总价<quota，那么最多只能扣减shopAmount的钱数
        Long benefitAmount = shopTotalAmount < quota ? shopTotalAmount : quota;
        long result = originTotalAmount - benefitAmount;
        log.info("MoneyOffRuleCalculator result: {}", result);
        return result;
    }
}
