package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户登录前相关请求的控制器
 * @author Change
 */
@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    /** 用户登录业务逻辑 */
    private LoginService loginService;

    /** 用户登录业务逻辑注入 */
    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("")
    public String login() {
        return loginService.test();
    }

    /**
     * 获取验证码图片，将其封装到response流中返回
     * @param response 要封装的response流
     * @param session 将验证码封装进session域
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpSession session) {
        //往响应对象中写入图片，并得到图片文本
        String text = loginService.verifyCode(response);
        //封装进session域
        session.setAttribute("verifyCodeText", text);
    }

    @RequestMapping("/mailVerifyCode")
    public Map<String, Object> mailVerifyCode(@RequestBody Map<String, String> requestMap, HttpSession session) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            loginService.mailVerifyCode(requestMap.get("username"));
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMsg("发送失败，请检查邮箱是否正确");
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("发送成功");
        return responseMessage.toMap();
    }

    @RequestMapping("/regist")
    public void regist(@RequestBody Map<String, String> requestMap) {
        System.out.println(requestMap.get("username"));
        System.out.println(requestMap.get("password"));
        System.out.println(requestMap.get("rePassword"));

    }

}
