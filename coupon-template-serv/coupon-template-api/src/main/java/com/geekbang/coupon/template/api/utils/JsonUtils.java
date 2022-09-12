package com.geekbang.coupon.template.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * JSON 工具类
 *
 * @author wuyuexiang
 * @date 2022年09月12日 01:17
 */
public class JsonUtils {

    private JsonUtils() {}

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJsonString(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return StringUtils.EMPTY;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String json, Class<T> type) {
        T value = null;
        if (ObjectUtils.isEmpty(json) || ObjectUtils.isEmpty(type)) {
            return null;
        }
        try {
            value = OBJECT_MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
