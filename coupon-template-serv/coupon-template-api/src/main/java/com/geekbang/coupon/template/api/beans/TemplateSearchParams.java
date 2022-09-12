package com.geekbang.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模版搜索参数
 *
 * @author wuyuexiang
 * @date 2022年09月12日 00:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateSearchParams {

    private Long id;

    private String name;

    private Integer type;

    private Long shopId;

    private Boolean available;

    private Integer page;

    private Integer pageSize;
}
