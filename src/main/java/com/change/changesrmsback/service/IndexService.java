package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.Admin;
import com.change.changesrmsback.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求主页相关业务操作
 * @author Change
 */
@Service
public class IndexService {

    public Map<String, Object> userData() throws Exception {
        Map<String, Object> result = new HashMap<>();
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            return null;
        }
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            result.put("username", user.getUserName());
            result.put("nickname", user.getUserNickname());
            result.put("role", "普通用户");
        } else if (principal instanceof Admin) {
            Admin admin = (Admin) principal;
            result.put("username", admin.getAdminName());
            result.put("nickname", admin.getAdminNickname());
            if ("general".equals(admin.getRole())) {
                result.put("role", "管理员");
            } else if ("super".equals(admin.getRole())) {
                result.put("role", "超级管理员");
            }
        } else {
            throw new Exception("获取用户信息失败");
        }
        return result;
    }
}
