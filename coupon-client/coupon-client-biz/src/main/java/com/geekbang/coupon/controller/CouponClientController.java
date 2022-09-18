package com.geekbang.coupon.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.geekbang.coupon.beans.ShoppingCart;
import com.geekbang.coupon.beans.SimulationOrder;
import com.geekbang.coupon.beans.SimulationResponse;
import com.geekbang.coupon.beans.RequestCoupon;
import com.geekbang.coupon.beans.SearchCoupon;
import com.geekbang.coupon.service.CouponClientService;
import com.geekbang.coupon.beans.CouponInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 优惠券客户端控制器
 *
 * @author wuyuexiang
 * @date 2022年09月14日 21:07
 */
@RestController
@RequestMapping("/client/coupon")
@RequiredArgsConstructor
@RefreshScope
@Slf4j
public class CouponClientController {

    private final CouponClientService couponClientService;

    @Value("${disableCouponRequest:false}")
    private Boolean disableCouponRequest;

    /**
     * 领券
     *
     * @param request RequestCoupon
     * @return CouponInfo
     */
    @PostMapping
    @SentinelResource(value = "request")
    public CouponInfo requestCoupon(@Valid @RequestBody RequestCoupon request) {
        if (disableCouponRequest) {
            log.info("暂停领券服务！");
            return null;
        }
        return couponClientService.requestCoupon(request);
    }

    /**
     * 用户删除优惠券
     *
     * @param customerId Long
     * @param couponId Long
     */
    @DeleteMapping("/{customerId}/{couponId}")
    @SentinelResource(value = "delete")
    public void deleteCoupon(@PathVariable("customerId") Long customerId,
                             @PathVariable("couponId") Long couponId) {
        couponClientService.deleteCoupon(customerId, couponId);
    }

    /**
     * 用户模拟计算每个优惠券的优惠价格
     *
     * @param order SimulationOrder
     * @return SimulationResponse
     */
    @PostMapping("/simulation")
    @SentinelResource(value = "simulate")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        return couponClientService.simulateOrderPrice(order);
    }

    /**
     * 核销优惠券
     *
     * @param cart ShoppingCart
     * @return ShoppingCart
     */
    @PostMapping("/place")
    @SentinelResource(value = "place")
    public ShoppingCart placeOrder(@Valid @RequestBody ShoppingCart cart) {
        return couponClientService.placeOrder(cart);
    }

    /**
     * 查询优惠券列表
     *
     * @param request SearchCoupon
     * @return List<CouponInfo>
     */
    @PostMapping("/list")
    @SentinelResource(value = "list")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        return couponClientService.findCoupon(request);
    }
}
