package com.geekbang.coupon.rule.calculator;

import com.geekbang.coupon.rule.AbstractRuleCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 晚间规则计算器
 *
 * @author wuyuexiang
 * @date 2022年09月13日 23:49
 */
@Component
@Slf4j
public class NightRuleCalculator extends AbstractRuleCalculator {

    private static final Integer NIGHT_START_HOUR = 23;

    private static final Integer NIGHT_END_HOUR = 2;

    private static final Integer NIGHT_MULTIPLE = 2;

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
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hourOfDay >= NIGHT_START_HOUR && hourOfDay < NIGHT_END_HOUR) {
            quota *= NIGHT_MULTIPLE;
        }
        Long benefitAmount = shopTotalAmount < quota ? shopTotalAmount : quota;
        long result = originTotalAmount - benefitAmount;
        log.info("NightRuleCalculator result: {}", result);
        return result;
    }
}
