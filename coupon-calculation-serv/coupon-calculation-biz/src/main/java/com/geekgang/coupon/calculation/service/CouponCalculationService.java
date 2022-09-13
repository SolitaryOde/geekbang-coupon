package com.geekgang.coupon.calculation.service;

import com.geekbang.coupon.calculation.api.beans.ShoppingCart;
import com.geekbang.coupon.calculation.api.beans.SimulationOrder;
import com.geekbang.coupon.calculation.api.beans.SimulationResponse;

/**
 * 优惠券计算服务
 *
 * @author wuyuexiang
 * @date 2022年09月14日 00:21
 */
public interface CouponCalculationService {

    /**
     * 计算订单价格
     *
     * @param cart 购物车
     * @return 购物车
     */
    ShoppingCart calculateOrderPrice(ShoppingCart cart);

    /**
     * 试算订单价格
     *
     * @param simulationOrder 试算订单
     * @return 试算结果
     */
    SimulationResponse simulateOrderPrice(SimulationOrder simulationOrder);
}
