package com.change.changesrmsback.utils;

import com.change.changesrmsback.entity.ResponseMessage;
import org.apache.shiro.SecurityUtils;

/**
 * 利用shiro中的SecurityUtils，
 * 验证是否已经登录以及是否有相应权限
 * @author Change
 */
public class RoleTest {

    /**
     * 判断是否有登录或者是否有“user”角色权限
     * @return 有则返回null，没有登录或者权限返回封装好的responseMessage
     */
    public static ResponseMessage hasRoleUser() {
        ResponseMessage responseMessage = new ResponseMessage();
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            responseMessage.setStatus(400);
            responseMessage.setMsg("请重新登录");
            return responseMessage;
        }
        if (SecurityUtils.getSubject().hasRole("user")) {
            return null;
        } else {
            responseMessage.setStatus(400);
            responseMessage.setMsg("该角色无权限");
            return responseMessage;
        }
    }

    /**
     * 判断是否有登录或者是否有“general”角色权限
     * @return 有则返回null，没有登录或者权限返回封装好的responseMessage
     */
    public static ResponseMessage hasRoleGeneral() {
        ResponseMessage responseMessage = new ResponseMessage();
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            responseMessage.setStatus(400);
            responseMessage.setMsg("请重新登录");
            return responseMessage;
        }
        if (SecurityUtils.getSubject().hasRole("general") ||
                SecurityUtils.getSubject().hasRole("super")) {
            return null;
        } else {
            responseMessage.setStatus(400);
            responseMessage.setMsg("该角色无权限");
            return responseMessage;
        }
    }

    /**
     * 判断是否有登录或者是否有“super”角色权限
     * @return 有则返回null，没有登录或者权限返回封装好的responseMessage
     */
    public static ResponseMessage hasRoleSuper() {
        ResponseMessage responseMessage = new ResponseMessage();
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            responseMessage.setStatus(400);
            responseMessage.setMsg("请重新登录");
            return responseMessage;
        }
        if (SecurityUtils.getSubject().hasRole("super")) {
            return null;
        } else {
            responseMessage.setStatus(400);
            responseMessage.setMsg("该角色无权限");
            return responseMessage;
        }
    }
}
