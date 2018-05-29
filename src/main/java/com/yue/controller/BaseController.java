package com.yue.controller;

import com.yue.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yue on 2018/5/16
 */
public class BaseController {

    static <T> String toJson(T entity, int code) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("code", code);
        result.put("data", entity);

        return JsonUtil.toJson(result);
    }

}
