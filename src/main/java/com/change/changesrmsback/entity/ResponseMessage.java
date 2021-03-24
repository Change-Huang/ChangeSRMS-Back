package com.change.changesrmsback.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器返回给浏览器的数据封装
 * @author Change
 */
@Data
public class ResponseMessage {
    private Integer status;
    private String msg;
    private Map<String, Object> data = new HashMap<>();

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
