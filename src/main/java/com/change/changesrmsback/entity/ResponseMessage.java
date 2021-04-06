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
    /** 状态码 */
    private Integer status;
    /** 状态码信息 */
    private String msg;
    /** 其他数据 */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 转换成map输出
     * @return 包含status、msg、data的map
     */
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    /**
     * 将另一个responseMessage的内容封装进这个
     * @param responseMessage 要封装的对象
     * @return 如果是true则成功，如果是false说明封装的对象是null
     */
    public boolean getOneResponseMessage(ResponseMessage responseMessage) {
        if (responseMessage == null) return false;
        status = responseMessage.getStatus();
        msg = responseMessage.getMsg();
        data = responseMessage.getData();
        return true;
    }
}
