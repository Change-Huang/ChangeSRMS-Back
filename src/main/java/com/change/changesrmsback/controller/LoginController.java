package com.change.changesrmsback.controller;

import com.change.changesrmsback.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录控制器
 * @author Change
 */
@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    /** 用户登录业务逻辑 */
    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("")
    public String login() {
        return loginService.test();
    }

}
