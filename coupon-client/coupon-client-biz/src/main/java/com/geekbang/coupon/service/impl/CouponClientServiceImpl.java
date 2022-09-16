package com.geekbang.coupon.service.impl;

import com.geekbang.coupon.feign.CouponCalculationService;
import com.geekbang.coupon.feign.CouponTemplateService;
import com.geekbang.coupon.repository.CouponRepository;
import com.geekbang.coupon.repository.entity.Coupon;
import com.geekbang.coupon.beans.ShoppingCart;
import com.geekbang.coupon.beans.SimulationOrder;
import com.geekbang.coupon.beans.SimulationResponse;
import com.geekbang.coupon.beans.RequestCoupon;
import com.geekbang.coupon.beans.SearchCoupon;
import com.geekbang.coupon.enumes.CouponStatus;
import com.geekbang.coupon.converter.CouponConverter;
import com.geekbang.coupon.service.CouponClientService;
import com.geekbang.coupon.beans.CouponInfo;
import com.geekbang.coupon.beans.CouponTemplateInfo;
import com.geekbang.coupon.supporter.WebClientSupporter;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 优惠券客户端服务
 *
 * @author wuyuexiang
 * @date 2022年09月14日 20:33
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CouponClientServiceImpl implements CouponClientService {

    private final CouponRepository couponRepository;

    private final CouponTemplateService couponTemplateService;

    private final CouponCalculationService couponCalculationService;

    /**
     * 领券接口
     *
     * @param request RequestCoupon
     * @return Coupon
     */
    @Override
    public CouponInfo requestCoupon(RequestCoupon request) {
        // 查询模版
        CouponTemplateInfo templateInfo = couponTemplateService.getTemplate(request.getCouponTemplateId());
        if (templateInfo == null) {
            log.error("invalid template id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("Invalid template id");
        }
        // 模板不能过期
        long now = Calendar.getInstance().getTimeInMillis();
        Long expTime = templateInfo.getRule().getDeadline();
        if (expTime != null && now >= expTime || !Boolean.TRUE.equals(templateInfo.getAvailable())) {
            log.error("template is not available id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("template is unavailable");
        }
        // 用户领券数量超过上限
        Long count = couponRepository.countByCustomerIdAndTemplateId(request.getCustomerId(),
                request.getCouponTemplateId());
        if (count >= templateInfo.getRule().getLimitation()) {
            log.error("exceeds maximum number");
            throw new IllegalArgumentException("exceeds maximum number");
        }
        // 创建优惠券
        Coupon coupon = Coupon.builder()
                .templateId(request.getCouponTemplateId())
                .customerId(request.getCustomerId())
                .shopId(templateInfo.getShopId())
                .status(CouponStatus.AVAILABLE)
                .build();
        coupon = couponRepository.save(coupon);
        return CouponConverter.convertEntityToBean(coupon);
    }

    /**
     * 核销优惠券
     *
     * @param cart ShoppingCart
     * @return ShoppingCart
     */
    @Override
    public ShoppingCart placeOrder(ShoppingCart cart) {
        if (CollectionUtils.isEmpty(cart.getProducts())) {
            log.error("invalid check out request, order={}", cart);
            throw new IllegalArgumentException("cart if empty");
        }

        Coupon coupon = null;
        if (!ObjectUtils.isEmpty(cart.getCouponId())) {
            // 如果有优惠券，验证是否可用，并且是当前客户的
            Coupon example = Coupon.builder()
                    .customerId(cart.getCustomerId())
                    .id(cart.getCouponId())
                    .status(CouponStatus.AVAILABLE)
                    .build();
            coupon = couponRepository.findAll(Example.of(example))
                    .stream()
                    .findFirst()
                    // 如果找不到券，就抛出异常
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));

            CouponInfo couponInfo = CouponConverter.convertEntityToBean(coupon);
            CouponTemplateInfo templateInfo = couponTemplateService.getTemplate(coupon.getTemplateId());
            couponInfo.setTemplate(templateInfo);
            cart.setCouponInfos(Lists.newArrayList(couponInfo));
        }

        // order清算
        ShoppingCart checkoutInfo = couponCalculationService.calculateOrderPrice(cart);

        if (coupon != null) {
            // 如果优惠券没有被结算掉，而用户传递了优惠券，报错提示该订单满足不了优惠条件
            if (CollectionUtils.isEmpty(checkoutInfo.getCouponInfos())) {
                log.error("cannot apply coupon to order, couponId={}", coupon.getId());
                throw new IllegalArgumentException("coupon is not applicable to this order");
            }

            log.info("update coupon status to used, couponId={}", coupon.getId());
            coupon.setStatus(CouponStatus.USED);
            couponRepository.save(coupon);
        }
        return checkoutInfo;
    }

    /**
     * 优惠券金额试算
     *
     * @param order SimulationOrder
     * @return SimulationResponse
     */
    @Override
    public SimulationResponse simulateOrderPrice(SimulationOrder order) {
        List<CouponInfo> couponInfos = Lists.newArrayList();
        // 挨个循环，把优惠券信息加载出来
        // 高并发场景下不能这么一个个循环，更好的做法是批量查询
        // 而且券模板一旦创建不会改内容，所以在创建端做数据异构放到缓存里，使用端从缓存捞template信息
        for (Long couponId : order.getCouponIds()) {
            Coupon example = Coupon.builder()
                    .customerId(order.getCustomerId())
                    .id(couponId)
                    .status(CouponStatus.AVAILABLE)
                    .build();
            Optional<Coupon> couponOptional = couponRepository.findAll(Example.of(example))
                    .stream()
                    .findFirst();
            // 加载优惠券模板信息
            if (couponOptional.isPresent()) {
                Coupon coupon = couponOptional.get();
                CouponInfo couponInfo = CouponConverter.convertEntityToBean(coupon);
                CouponTemplateInfo templateInfo = couponTemplateService.getTemplate(coupon.getTemplateId());
                couponInfo.setTemplate(templateInfo);
                couponInfos.add(couponInfo);
            }
        }
        order.setCouponInfos(couponInfos);
        // 调用接口试算服务
        return couponCalculationService.simulate(order);
    }

    /**
     * 删除优惠券
     *
     * @param customerId 客户id
     * @param couponId 优惠券id
     */
    @Override
    public void deleteCoupon(Long customerId, Long couponId) {
        Coupon example = Coupon.builder()
                .customerId(customerId)
                .id(couponId)
                .status(CouponStatus.AVAILABLE)
                .build();
        Coupon coupon = couponRepository.findAll(Example.of(example))
                .stream()
                .findFirst()
                // 如果找不到券，就抛出异常
                .orElseThrow(() -> new RuntimeException("Could not find available coupon"));

        coupon.setStatus(CouponStatus.INACTIVE);
        couponRepository.save(coupon);
    }

    /**
     * 查询用户优惠券
     *
     * @param request SearchCoupon
     * @return List<CouponInfo>
     */
    @Override
    public List<CouponInfo> findCoupon(SearchCoupon request) {
        // 在真正的生产环境，这个接口需要做分页查询，并且查询条件要封装成一个类
        Coupon example = Coupon.builder()
                .customerId(request.getCustomerId())
                .status(CouponStatus.convert(request.getCouponStatus()))
                .shopId(request.getShopId())
                .build();
        // 这里你可以尝试实现分页查询
        List<Coupon> coupons = couponRepository.findAll(Example.of(example));
        if (coupons.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Long> templateIds = coupons.stream()
                .map(Coupon::getTemplateId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, CouponTemplateInfo> templateMap = couponTemplateService.getTemplateInBatch(templateIds);
        if (!ObjectUtils.isEmpty(templateMap)) {
            coupons.forEach(e -> e.setTemplateInfo(templateMap.get(e.getTemplateId())));
        }
        return coupons.stream()
                .map(CouponConverter::convertEntityToBean)
                .collect(Collectors.toList());
    }
}
