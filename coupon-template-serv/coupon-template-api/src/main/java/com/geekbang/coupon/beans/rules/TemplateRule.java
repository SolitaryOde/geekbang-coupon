package com.geekbang.coupon.beans.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模版规则
 *
 * @author wuyuexiang
 * @date 2022年09月12日 00:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateRule {

    // 可以享受的折扣
    private Discount discount;

    // 每个人最多可以领券数量
    private Integer limitation;

    // 过期时间
    private Long deadline;
}
