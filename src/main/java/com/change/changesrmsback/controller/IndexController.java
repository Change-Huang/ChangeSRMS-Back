package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    /** 主页相关操作 */
    private IndexService indexService;

    /** 主页相关操作注入 */
    @Autowired
    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }

    /**
     * 前端获取当前登录的用户数据的接口
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --302，用户未登录<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，其他错误<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --userName，用户的用户名<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --nickname，用户的昵称<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --role，用户角色
     */
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

    /**
     * 用户修改密码接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --old，原密码<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --new，新密码<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --renew，确认新密码<br>
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，其他错误<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/updatePassword")
    public Map<String, Object> updatePassword(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        // 获取数据
        String oldP = (String) requestMap.get("old");
        String newP = (String) requestMap.get("new");
        String renewP = (String) requestMap.get("renew");
        try {
            // 操作
            indexService.updatePassword(oldP, newP, renewP);
        } catch (Exception e) {
            // 封装返回
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("修改密码成功");
        return responseMessage.toMap();
    }
}
