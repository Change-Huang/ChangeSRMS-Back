package com.change.changesrmsback.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用于前后端分离多Realm认证的核心
 * 重写了UsernamePasswordToken类
 * 增加了role属性，并且添加了构造函数和get、set方法
 * 在Realm中认证获得Token后可以进行判断
 * @author Change
 */
public class UsernamePasswordRoleToken extends UsernamePasswordToken {

    /** 用户类别，认证时候的service层传入，让Realm能进行分辨 */
    private String role;

    /**
     * UsernamePasswordUsertypeToken重写的构造方法
     * @param loginName 用户名
     * @param password  密码
     * @param role      认证的用户的类型，允许在Realm中取出进行判断
     */
    public UsernamePasswordRoleToken(String loginName, String password, String role) {
        super(loginName, password);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
