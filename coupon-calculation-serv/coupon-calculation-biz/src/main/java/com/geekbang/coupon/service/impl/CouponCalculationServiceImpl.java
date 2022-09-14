package com.geekbang.coupon.service.impl;

import com.geekbang.coupon.beans.ShoppingCart;
import com.geekbang.coupon.beans.SimulationOrder;
import com.geekbang.coupon.beans.SimulationResponse;
import com.geekbang.coupon.beans.CouponInfo;
import com.geekbang.coupon.beans.CouponTemplateInfo;
import com.geekbang.coupon.enums.CouponType;
import com.geekbang.coupon.rule.RuleCalculator;
import com.geekbang.coupon.rule.RuleCalculatorFactory;
import com.geekbang.coupon.service.CouponCalculationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 优惠券计算服务
 *
 * @author wuyuexiang
 * @date 2022年09月14日 00:25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CouponCalculationServiceImpl implements CouponCalculationService {

    private final RuleCalculatorFactory ruleCalculatorFactory;

    /**
     * 计算订单价格
     *
     * @param cart 购物车
     * @return 购物车
     */
    @Override
    public ShoppingCart calculateOrderPrice(ShoppingCart cart) {
        CouponType couponType = getCouponTypeFromCart(cart);
        RuleCalculator ruleCalculator = ruleCalculatorFactory.getRuleCalculator(couponType);
        ShoppingCart resultCart = ruleCalculator.calculate(cart);
        log.info("calculate order price: {}", resultCart.getCost());
        return resultCart;
    }

    /**
     * 试算订单价格
     *
     * @param simulationOrder 试算订单
     * @return 试算结果
     */
    @Override
    public SimulationResponse simulateOrderPrice(SimulationOrder simulationOrder) {
        SimulationResponse response = new SimulationResponse();
        long minOrderPrice = Long.MAX_VALUE;

        // 计算每一个优惠券的订单价格
        for (CouponInfo coupon : simulationOrder.getCouponInfos()) {
            ShoppingCart cart = new ShoppingCart();
            cart.setProducts(simulationOrder.getProducts());
            cart.setCouponInfos(Lists.newArrayList(coupon));
            cart = ruleCalculatorFactory.getRuleCalculator(getCouponTypeFromCart(cart)).calculate(cart);

            Long couponId = coupon.getId();
            Long orderPrice = cart.getCost();

            // 设置当前优惠券对应的订单价格
            if (response.getCouponToOrderPrice() == null) {
                response.setCouponToOrderPrice(Maps.newHashMap());
            }
            response.getCouponToOrderPrice().put(couponId, orderPrice);

            // 比较订单价格，设置当前最优优惠券的ID
            if (minOrderPrice > orderPrice) {
                response.setBestCouponId(coupon.getId());
                minOrderPrice = orderPrice;
            }
        }
        return response;
    }

    /**
     * 从购物车获取优惠券类型
     *
     * @param cart 购物车
     * @return 优惠券类型
     */
    private CouponType getCouponTypeFromCart(ShoppingCart cart) {
        Integer typeCode = cart.getCouponInfos().stream()
                .map(CouponInfo::getTemplate)
                .map(CouponTemplateInfo::getType)
                .findFirst()
                .orElse(null);
        return CouponType.convert(typeCode);
    }
}
