package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户登录前相关请求的控制器
 * @author Change
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /** 用户登录业务逻辑 */
    private LoginService loginService;

    /** 用户登录业务逻辑注入 */
    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 获取验证码图片，将其封装到response流中返回
     * @param response 要封装的response流
     * @param session  将验证码封装进session域
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpSession session) {
        //往响应对象中写入图片，并得到图片文本
        String text = loginService.verifyCode(response);
        //封装进session域
        session.setAttribute("sessionVerifyCode", text);
    }

    /**
     * 用户登录
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --username，用户名<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --password，密码<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --verifyCode，验证码<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --role，角色，包括user和admin
     * @param session    用于获取验证码
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，登录成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，登录失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> requestMap, HttpSession session) {
        String username = requestMap.get("username");
        String password = requestMap.get("password");
        String verifyCode = requestMap.get("verifyCode");
        String role = requestMap.get("role");
        String sessionVerifyCode = (String) session.getAttribute("sessionVerifyCode");
        System.out.println(sessionVerifyCode);
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            loginService.login(username, password, verifyCode, role, sessionVerifyCode);
        } catch (AuthenticationException e) {
            responseMessage.setStatus(400);
            responseMessage.setMsg("用户名或密码错误");
            return responseMessage.toMap();
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("登录成功");
        return responseMessage.toMap();
    }

    /**
     * 发送邮箱验证码请求，并将邮箱及验证码保存到session中
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp; --username，用户要注册的邮箱
     * @param session    用于保存邮箱和验证码
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，发送成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，发送失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/mailVerifyCode")
    public Map<String, Object> mailVerifyCode(@RequestBody Map<String, String> requestMap, HttpSession session) {
        ResponseMessage responseMessage = new ResponseMessage();
        String code;
        String username = requestMap.get("username");
        try {
            code = loginService.mailVerifyCode(username);
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        session.setAttribute("sessionMailVerifyCode", code);
        session.setAttribute("sessionRegistMail", username);
        responseMessage.setStatus(200);
        responseMessage.setMsg("发送成功");
        return responseMessage.toMap();
    }

    /**
     * 用户注册
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --username，用户要注册的邮箱<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --nickname，用户的姓名<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --password，用户的密码<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --rePassword，确认密码<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --verifyMailCode，正确的邮箱验证码
     * @param session    用于取出已经保存的邮箱和验证码
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，注册成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，注册失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/regist")
    public Map<String, Object> regist(@RequestBody Map<String, String> requestMap, HttpSession session) {
        String username = requestMap.get("username");
        String nickname = requestMap.get("nickname");
        String password = requestMap.get("password");
        String rePassword = requestMap.get("rePassword");
        String verifyMailCode = requestMap.get("verifyMailCode");
        String sessionMailVerifyCode = (String) session.getAttribute("sessionMailVerifyCode");
        String sessionRegistMail = (String) session.getAttribute("sessionRegistMail");
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            loginService.regist(username, nickname, password, rePassword, verifyMailCode,
                    sessionMailVerifyCode, sessionRegistMail);
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("注册成功");
        return responseMessage.toMap();
    }

    /**
     * 用户忘记密码
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --username，用户忘记密码的邮箱<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --verifyCode，验证码
     * @param session    用于取出已经保存的验证码
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/forget")
    public Map<String, Object> forget(@RequestBody Map<String, String> requestMap, HttpSession session) {
        String username = requestMap.get("username");
        String verifyCode = requestMap.get("verifyCode");
        String sessionVerifyCode = (String) session.getAttribute("sessionVerifyCode");
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            loginService.forget(username, verifyCode, sessionVerifyCode);
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("请留意你的邮箱");
        return responseMessage.toMap();
    }

    /**
     * 用户退出登录，直接调用shiro的方法
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，成功<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/logout")
    public Map<String, Object> logout() {
        ResponseMessage responseMessage = new ResponseMessage();
        loginService.logout();
        responseMessage.setStatus(200);
        responseMessage.setMsg("退出登录成功");
        return responseMessage.toMap();
    }

    // todo 如果最后用不上就删了
    @RequestMapping("/islogin")
    public Map<String, Object> isLogin() {
        ResponseMessage responseMessage = new ResponseMessage();
        if (loginService.isLogin()) {
            responseMessage.setStatus(200);
            responseMessage.setMsg("已经登录");
        } else {
            responseMessage.setStatus(400);
            responseMessage.setMsg("未登录");
        }
        return responseMessage.toMap();
    }
}
