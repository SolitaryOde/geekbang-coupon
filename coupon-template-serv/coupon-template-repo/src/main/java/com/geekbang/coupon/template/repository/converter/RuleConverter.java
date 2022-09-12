package com.geekbang.coupon.template.repository.converter;

import com.geekbang.coupon.template.api.beans.rules.TemplateRule;
import com.geekbang.coupon.template.api.utils.JsonUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 * @author wuyuexiang
 * @date 2022年09月12日 01:15
 */
@Convert
public class RuleConverter implements AttributeConverter<TemplateRule, String> {

    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JsonUtils.toJsonString(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return JsonUtils.parseObject(rule, TemplateRule.class);
    }
}
