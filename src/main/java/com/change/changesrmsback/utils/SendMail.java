package com.change.changesrmsback.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * 邮件发送工具类封装
 * @author Change
 */
@Component
public class SendMail {

    /** 邮件发送对象 */
    private MailSender mailSender;

    /** 发件人 */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 构造函数
     * @param mailSender 传入邮件发送对象，一般可通过自动注入org.springframework.
     *                   mail.javamail.JavaMailSender类来得到
     */
    public SendMail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 普通封装的发送邮件
     * @param subject 邮件主题
     * @param to      邮件接收者
     * @param text    邮件正文
     */
    public void send(String subject, String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * 发送包含验证码的注册邮件
     * @param to   邮件接收者
     * @param code 邮件验证码
     */
    public void sendRegist(String to, String code) {
        String subject = "Change SRMS 注册验证码";
        String text = "您好<br>感谢使用 Change SRMS 场地预约管理系统<br>您的验证码为：" + code;
        send(subject, to, text);
    }

    /**
     * 发送包含密码的邮件给忘记密码的用户
     * @param to       邮件接收者
     * @param password 登陆密码
     */
    public void sendForget(String to, String password) {
        String subject = "Change SRMS 忘记密码";
        String text = "您好<br>感谢使用 Change SRMS 场地预约管理系统<br>您的密码为：" + password +
                "<br>请尽快登陆后并修改密码";
        send(subject, to, text);
    }
}
