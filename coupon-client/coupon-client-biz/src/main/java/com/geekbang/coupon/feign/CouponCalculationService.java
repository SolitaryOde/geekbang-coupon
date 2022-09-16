package com.geekbang.coupon.feign;

import com.geekbang.coupon.beans.ShoppingCart;
import com.geekbang.coupon.beans.SimulationOrder;
import com.geekbang.coupon.beans.SimulationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 优惠券计算接口
 *
 * @author wuyuexiang
 * @date 2022年09月16日 23:21
 */
@FeignClient(value = "coupon-calculation-serv", path = "/coupon/calculation")
public interface CouponCalculationService {

    /**
     * 优惠券结算
     *
     * @param shoppingCart 购物车
     * @return 结算结果
     */
    @PostMapping
    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart shoppingCart);

    /**
     * 优惠券列表挨个试算
     * 给客户提示每个可用券的优惠额度，帮助挑选
     *
     * @param simulationOrder 试算订单
     * @return 试算结果
     */
    @PostMapping("/simulation")
    SimulationResponse simulate(@RequestBody SimulationOrder simulationOrder);
}
