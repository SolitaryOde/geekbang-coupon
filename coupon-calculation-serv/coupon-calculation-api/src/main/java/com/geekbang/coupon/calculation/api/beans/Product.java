package com.geekbang.coupon.calculation.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品类
 *
 * @author wuyuexiang
 * @date 2022年09月13日 21:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long price;

    private Integer count;

    private Long shopId;
}
