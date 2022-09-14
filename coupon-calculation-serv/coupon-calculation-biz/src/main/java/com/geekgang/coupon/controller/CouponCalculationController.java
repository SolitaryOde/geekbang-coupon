package com.geekgang.coupon.controller;

import com.geekbang.coupon.beans.ShoppingCart;
import com.geekbang.coupon.beans.SimulationOrder;
import com.geekbang.coupon.beans.SimulationResponse;
import com.geekgang.coupon.service.CouponCalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠券计算控制器
 *
 * @author wuyuexiang
 * @date 2022年09月14日 01:04
 */
@RestController
@RequestMapping("/coupon/calculation")
@RequiredArgsConstructor
@Slf4j
public class CouponCalculationController {

    private final CouponCalculationService couponCalculationService;

    /**
     * 优惠券结算
     *
     * @param shoppingCart 购物车
     * @return 结算结果
     */
    @PostMapping
    public ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart shoppingCart) {
        log.info("do calculation: {}", shoppingCart);
        return couponCalculationService.calculateOrderPrice(shoppingCart);
    }

    /**
     * 优惠券列表挨个试算
     * 给客户提示每个可用券的优惠额度，帮助挑选
     *
     * @param simulationOrder 试算订单
     * @return 试算结果
     */
    @PostMapping("/simulation")
    public SimulationResponse simulate(@RequestBody SimulationOrder simulationOrder) {
        log.info("do simulation: {}", simulationOrder);
        return couponCalculationService.simulateOrderPrice(simulationOrder);
    }
}
