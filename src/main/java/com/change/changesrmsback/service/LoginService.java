package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.mapper.AdminMapper;
import com.change.changesrmsback.mapper.UserMapper;
import com.change.changesrmsback.shiro.UsernamePasswordRoleToken;
import com.change.changesrmsback.utils.CommonUtils;
import com.change.changesrmsback.utils.SendMail;
import com.change.changesrmsback.utils.SnowflakeIdWorker;
import com.change.changesrmsback.utils.VerifyCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 用户登录业务逻辑
 * @author Change
 */
@Service
public class LoginService {

    /** 用户登录持久层 */
    private UserMapper userMapper;
    /** 管理员持久层 */
    private AdminMapper adminMapper;

    // todo 如果后面没用到就删了
    /** 邮件发送类 */
    private JavaMailSender javaMailSender;
    /** 邮件发送工具封装 */
    private SendMail sendMail;

    /** 用户登录持久层注入 */
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /** 管理员持久层注入 */
    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /** 邮件发送类注入 */
    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /** 邮件发送工具封装 */
    @Autowired
    public void setSendMail(SendMail sendMail) {
        this.sendMail = sendMail;
    }

    /**
     * 通过验证码类获取验证码图片，并写入到response流中返回到前端
     * @param response 要写入的response流
     * @return 获取到的验证码的正确文本
     */
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

    /**
     * 登录业务，交给shiro去做，其中用到自定义的token
     * @param username          用户名
     * @param password          密码
     * @param verifyCode        验证码
     * @param role              角色，为user或者admin
     * @param sessionVerifyCode 正确的验证码
     * @throws Exception 抛出异常的原因包括验证码、用户名、密码输入不正确
     */
    public void login(String username, String password, String verifyCode,
                      String role, String sessionVerifyCode)
            throws Exception {
        // 数据校验
        // todo 开发阶段不校验验证码
//        if (verifyCode == null || "".equals(verifyCode) || !verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
//            throw new Exception("输入的验证码有误");
//        }
        if (username == null || "".equals(username)) {
            throw new Exception("用户名不能为空");
        }
        if (password == null || "".equals(password)) {
            throw new Exception("密码不能为空");
        }
        // 认证
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordRoleToken token = new UsernamePasswordRoleToken(username, password, role);
        subject.login(token);
    }

    /**
     * 向指定的邮箱发送随机验证码，验证码由四位数组成
     * @param username 要发送验证码的邮箱
     * @return 返回已经发送的四位数随机验证码
     * @throws Exception 如果邮箱为空，抛出异常；如果已被注册，抛出异常；如果发送失败，抛出异常
     */
    public String mailVerifyCode(String username) throws Exception {
        // 数据验证
        if (username == null || "".equals(username)) {
            throw new Exception("发送失败，请检查邮箱是否正确");
        }
        // 查询是否已经存在
        if (userMapper.selectOneUserByUserName(username) != null) {
            throw new Exception("该用户已经被注册");
        }
        // 操作
        String randomCode = CommonUtils.getRandomCode();
        sendMail.sendRegist(username, randomCode);
        return randomCode;
    }

    /**
     * 用户注册<br> 先进行数据验证，如果都没有问题则将数据封装，之后保存到数据库
     * @param username              前端填入的用户名
     * @param password              前端填入的密码
     * @param rePassword            前端填入的确认密码
     * @param verifyMailCode        前端填入的邮箱验证码
     * @param sessionMailVerifyCode 已经发送至邮箱的验证码
     * @param sessionRegistMail     已经发送验证码的邮箱
     * @throws Exception 如果表单有错误则抛出异常
     */
    public void regist(String username, String password, String rePassword, String verifyMailCode,
                       String sessionMailVerifyCode, String sessionRegistMail)
            throws Exception {
        // 数据验证
        if (verifyMailCode == null || "".equals(verifyMailCode) || !verifyMailCode.equals(sessionMailVerifyCode)) {
            throw new Exception("输入的验证码有误");
        }
        if (username == null || "".equals(username) || !username.equals(sessionRegistMail)) {
            throw new Exception("输入的用户名有误");
        }
        if (password == null || "".equals(password) || !password.equals(rePassword)
                || password.length() < 6 || password.length() > 16) {
            throw new Exception("输入的密码有误");
        }
        // 数据封装
        User user = new User();
        user.setId(new SnowflakeIdWorker(0, 0).nextId());
        user.setUserName(username);
        user.setUserPassword(password);
        // 写入数据库
        int result = userMapper.insertOneUser(user);
        if (result != 1) {
            throw new Exception("注册失败，请重试");
        }
    }

    /**
     * 忘记密码业务，除了判断用户，也会判断管理员，默认用户和管理员没有一样的用户名
     * @param username          忘记密码的用户名
     * @param verifyCode        验证码
     * @param sessionVerifyCode 正确的验证码
     * @throws Exception 用户名或验证码有误时，抛出异常；在用户表中找不到，抛出异常
     */
    public void forget(String username, String verifyCode, String sessionVerifyCode)
            throws Exception {
        // 数据验证
        if (username == null || "".equals(username)) {
            throw new Exception("输入的用户名有误");
        }
        if (sessionVerifyCode == null || verifyCode == null ||
                "".equals(verifyCode) || !sessionVerifyCode.equalsIgnoreCase(verifyCode)) {
            throw new Exception("输入的验证码有误");
        }
        // 查询是否存在
        User user = userMapper.selectOneUserByUserName(username);
        if (user == null) {
            throw new Exception("该用户未被注册");
        }
        sendMail.sendForget(username, user.getUserPassword());
    }

    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 判断该用户是否已经认证登录
     * @return 已经登录返回true，没有则返回false
     */
    // todo 如果最后用不上就删了
    public boolean isLogin() {
        return SecurityUtils.getSubject().isAuthenticated();
    }
}
