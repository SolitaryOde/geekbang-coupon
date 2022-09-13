package com.geekgang.coupon.calculation.rule;

import com.geekbang.coupon.template.api.enums.CouponType;
import com.geekgang.coupon.calculation.rule.calculator.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

/**
 * 规则计算器工厂
 *
 * @author wuyuexiang
 * @date 2022年09月14日 00:28
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RuleCalculatorFactory {

    private final DiscountRuleCalculator discountRuleCalculator;

    private final DummyRuleCalculator dummyRuleCalculator;

    private final MoneyOffRuleCalculator moneyOffRuleCalculator;

    private final NightRuleCalculator nightRuleCalculator;

    private final RandomReductionRuleCalculator randomReductionRuleCalculator;

    /**
     * 获取规则计算器
     *
     * @param couponType 优惠券类型
     * @return 规则计算器
     */
    public RuleCalculator getRuleCalculator(CouponType couponType) {
        if (ObjectUtils.isEmpty(couponType)) {
            return dummyRuleCalculator;
        }
        switch (couponType) {
            case MONEY_OFF:
                return moneyOffRuleCalculator;
            case DISCOUNT:
                return discountRuleCalculator;
            case RANDOM_DISCOUNT:
                return randomReductionRuleCalculator;
            case NIGHT_MONEY_OFF:
                return nightRuleCalculator;
            default:
                return dummyRuleCalculator;
        }
    }
}
