package com.change.changesrmsback.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器返回给浏览器的数据封装
 * @author Change
 */
public class ResponseMessage {
    private int status;
    private String msg;
    private Map<String, Object> data = new HashMap<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
