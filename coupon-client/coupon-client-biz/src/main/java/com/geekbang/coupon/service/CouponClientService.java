package com.geekbang.coupon.service;

import com.geekbang.coupon.beans.ShoppingCart;
import com.geekbang.coupon.beans.SimulationOrder;
import com.geekbang.coupon.beans.SimulationResponse;
import com.geekbang.coupon.beans.RequestCoupon;
import com.geekbang.coupon.beans.SearchCoupon;
import com.geekbang.coupon.beans.CouponInfo;

import java.util.List;

/**
 * 优惠券客户端服务
 *
 * @author wuyuexiang
 * @date 2022年09月14日 20:29
 */
public interface CouponClientService {

    /**
     * 领券接口
     *
     * @param request RequestCoupon
     * @return Coupon
     */
    CouponInfo requestCoupon(RequestCoupon request);

    /**
     * 核销优惠券
     *
     * @param cart ShoppingCart
     * @return ShoppingCart
     */
    ShoppingCart placeOrder(ShoppingCart cart);

    /**
     * 优惠券金额试算
     *
     * @param order SimulationOrder
     * @return SimulationResponse
     */
    SimulationResponse simulateOrderPrice(SimulationOrder order);

    /**
     * 删除优惠券
     *
     * @param customerId 客户id
     * @param couponId 优惠券id
     */
    void deleteCoupon(Long customerId, Long couponId);

    /**
     * 查询用户优惠券
     *
     * @param request SearchCoupon
     * @return List<CouponInfo>
     */
    List<CouponInfo> findCoupon(SearchCoupon request);
}
