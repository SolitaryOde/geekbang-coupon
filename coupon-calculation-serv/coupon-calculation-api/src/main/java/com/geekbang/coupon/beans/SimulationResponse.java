package com.geekbang.coupon.beans;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 试算结果
 *
 * @author wuyuexiang
 * @date 2022年09月13日 21:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulationResponse {

    // 最省钱的coupon
    private Long bestCouponId;

    // 每一个coupon对应的order价格
    private Map<Long, Long> couponToOrderPrice = Maps.newHashMap();
}
