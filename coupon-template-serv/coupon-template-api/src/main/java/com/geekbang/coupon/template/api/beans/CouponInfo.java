package com.geekbang.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 优惠券信息
 *
 * @author wuyuexiang
 * @date 2022年09月12日 00:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponInfo {

    private Long id;

    private Long templateId;

    private Long customerId;

    private Long shopId;

    private Integer status;

    private CouponTemplateInfo template;
}
