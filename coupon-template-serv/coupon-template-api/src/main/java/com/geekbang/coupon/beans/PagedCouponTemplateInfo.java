package com.geekbang.coupon.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页优惠券模版信息
 *
 * @author wuyuexiang
 * @date 2022年09月12日 00:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedCouponTemplateInfo {

    private List<CouponTemplateInfo> templates;

    private Integer page;

    private Long total;
}
