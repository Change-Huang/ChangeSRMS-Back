package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 主页获取相关渲染数据的请求控制器
 * @author Change
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    private IndexService indexService;

    @Autowired
    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }

    @RequestMapping("/userData")
    public Map<String, Object> userData() {
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String, Object> data;
        try {
            data = indexService.userData();
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMsg("获取用户信息失败");
            return responseMessage.toMap();
        }
        if (data == null) {
            responseMessage.setStatus(302);
            responseMessage.setMsg("用户未登录");
        } else {
            responseMessage.setStatus(200);
            responseMessage.setMsg("获取用户信息成功");
            responseMessage.setData(data);
        }
        return responseMessage.toMap();
    }
}
