package com.change.changesrmsback.service;

import com.change.changesrmsback.mapper.UserMapper;
import com.change.changesrmsback.utils.CommonUtils;
import com.change.changesrmsback.utils.SendMail;
import com.change.changesrmsback.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 用户登录业务逻辑
 * @author Change
 */
@Service
public class LoginService {

    /** 用户登录持久层 */
    private UserMapper userMapper;

    /** 邮件发送者 */
    @Value("${spring.mail.username}")
    private String from;

    /** 邮件发送类 */
    private JavaMailSender javaMailSender;

    /** 用户登录持久层注入 */
    @Autowired
    public void UserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /** 邮件发送类注入 */
    @Autowired
    public void JavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String test() {
        return userMapper.selectTest();
    }

    public String verifyCode(HttpServletResponse response) {
        //实例化验证码
        VerifyCode vCode = new VerifyCode();
        //得到图片
        BufferedImage image = vCode.getImage();
        //将图片保存到response的输出流
        try {
            VerifyCode.output(image, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回
        return vCode.getText();
    }

    public String mailVerifyCode(String to) {
        String randomCode = CommonUtils.getRandomCode();
        SendMail sendMail = new SendMail(javaMailSender, from);
        sendMail.sendRegist(to, randomCode);
        return randomCode;
    }

}
