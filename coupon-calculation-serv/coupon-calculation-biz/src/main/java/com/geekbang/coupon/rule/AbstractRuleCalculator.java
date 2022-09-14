package com.geekbang.coupon.rule;

import com.geekbang.coupon.beans.Product;
import com.geekbang.coupon.beans.ShoppingCart;
import com.geekbang.coupon.beans.CouponTemplateInfo;
import com.geekbang.coupon.beans.rules.Discount;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 规则计算器模版
 *
 * @author wuyuexiang
 * @date 2022年09月13日 22:35
 */
@Slf4j
public abstract class AbstractRuleCalculator implements RuleCalculator {

    private static final Long MIN_COST = 1L;

    /**
     * 计算购物车的优惠券
     *
     * @param target 目标购物车
     * @return 计算结果封装到购物车
     */
    @Override
    public ShoppingCart calculate(ShoppingCart target) {
        // 获取订单总价
        Long originTotalAmount = getTotalPrice(target.getProducts());
        // 获取以shopId为维度的价格统计
        Map<Long, Long> sumAmount = getTotalPriceGroupByShop(target.getProducts());

        // 以下规则只做单个优惠券的计算
        CouponTemplateInfo template = target.getCouponInfos().get(0).getTemplate();
        Discount discount = template.getRule().getDiscount();
        // 最低消费限制
        Long threshold = discount.getThreshold();
        // 优惠金额或者打折比例
        Long quota = discount.getQuota();
        // 当前优惠券适用的门店ID，如果为空则作用于全店券
        Long shopId = template.getShopId();
        // 如果优惠券未指定shopId，shopTotalAmount=orderTotalAmount
        // 如果指定了shopId，则shopTotalAmount=对应门店下商品总价
        Long shopTotalAmount = (ObjectUtils.isEmpty(shopId)) ? originTotalAmount : sumAmount.get(shopId);

        // 如果不符合优惠券使用标准, 则直接按原价走，不使用优惠券
        if (ObjectUtils.isEmpty(shopTotalAmount) || shopTotalAmount < threshold) {
            log.warn("Totals of amount not meet, ur coupons are not applicable to this order");
            target.setCost(originTotalAmount);
            target.setCouponInfos(Collections.emptyList());
            return target;
        }

        // 子类中计算新的价格
        Long newCost = calculateNewPrice(originTotalAmount, shopTotalAmount, quota);
        // 订单价格不能小于最低价格
        if (newCost < MIN_COST) {
            newCost = MIN_COST;
        }
        target.setCost(newCost);
        log.info("original price = {}, new price = {}", originTotalAmount, newCost);
        return target;
    }

    /**
     * 金额计算具体逻辑，延迟到子类实现
     *
     * @param originTotalAmount 订单原始总金额
     * @param shopTotalAmount 店铺总金额
     * @param quota 优惠金额或者打折比例
     * @return 优惠后的订单金额
     */
    protected abstract Long calculateNewPrice(Long originTotalAmount, Long shopTotalAmount, Long quota);

    /**
     * 计算订单总价
     *
     * @param products 商品列表
     * @return 订单总价
     */
    protected Long getTotalPrice(List<Product> products) {
        return products.stream()
                .mapToLong(product -> product.getPrice() * product.getCount())
                .sum();
    }

    /**
     * 根据门店维度计算每个门店下商品价格
     * key = shopId
     * value = 门店商品总价
     *
     * @param products 商品列表
     * @return 每个门店下商品价格
     */
    protected Map<Long, Long> getTotalPriceGroupByShop(List<Product> products) {
        return products.stream()
                .collect(
                        Collectors.groupingBy(
                                Product::getShopId,
                                Collectors.summingLong(p -> p.getPrice() * p.getCount())
                        )
                );
    }

    /**
     * 封装 BigDecimal
     *
     * @param value Double
     * @return long
     */
    protected long convertToDecimal(Double value) {
        return new BigDecimal(value).setScale(0, RoundingMode.HALF_UP).longValue();
    }
}
