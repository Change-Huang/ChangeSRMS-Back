package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.Admin;
import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.mapper.AdminMapper;
import com.change.changesrmsback.mapper.HistoryMapper;
import com.change.changesrmsback.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求主页相关业务操作
 * @author Change
 */
@Service
public class IndexService {

    /** 用户登录数据访问层 */
    private UserMapper userMapper;

    /** 管理员数据访问层 */
    private AdminMapper adminMapper;

    /** 借用历史数据访问层 */
    private HistoryMapper historyMapper;

    /** 用户登录数据访问层注入 */
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /** 管理员数据访问层注入 */
    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /** 借用历史数据访问层注入 */
    @Autowired
    public void setHistoryMapper(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    /**
     * 获取用户信息
     * @return 返回包括用户名、用户昵称、用户角色的map；<br>
     *         如果用户未登录返回null
     * @throws Exception 出现其他错误时抛出
     */
    public Map<String, Object> userData() throws Exception {
        Map<String, Object> result = new HashMap<>();
        // 判断是否已经登录
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            return null;
        }
        // 如果登录，获取对象
        Object principal = SecurityUtils.getSubject().getPrincipal();
        // 看看对象是属于用户还是管理员
        if (principal instanceof User) {
            // 如果是用户
            User user = (User) principal;
            result.put("username", user.getUserName());
            result.put("nickname", user.getUserNickname());
            result.put("role", "user");
        } else if (principal instanceof Admin) {
            // 如果是管理员
            Admin admin = (Admin) principal;
            result.put("username", admin.getAdminName());
            result.put("nickname", admin.getAdminNickname());
            result.put("role", admin.getRole());
        } else {
            // 如果都不是，抛出异常
            throw new Exception("获取用户信息失败");
        }
        return result;
    }

    /**
     * 在主页修改密码的接口
     * @param oldP   原密码
     * @param newP   新密码
     * @param renewP 确认新密码
     * @throws Exception 用户未登录、表单错误、数据修改错误等
     */
    public void updatePassword(String oldP, String newP, String renewP) throws Exception {
        // 获取登录者的信息
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new Exception("用户未登录");
        }
        // 获取登录对象
        Object principal = SecurityUtils.getSubject().getPrincipal();
        long id;
        String readPassword;
        int version;
        // 判断该对象的类型，获取其id和密码
        if (principal instanceof User) {
            id = ((User) principal).getId();
            readPassword = ((User) principal).getUserPassword();
            version = ((User) principal).getVersion();
        } else if (principal instanceof Admin) {
            id = ((Admin) principal).getId();
            readPassword = ((Admin) principal).getAdminPassword();
            version = ((Admin) principal).getVersion();
        } else {
            throw new Exception("获取用户信息失败");
        }
        // 数据校验
        if (!readPassword.equals(oldP)) {
            throw new Exception("旧密码输入错误");
        }
        if ("".equals(newP) || newP == null || newP.length() < 6 || newP.length() > 16) {
            throw new Exception("新密码输入错误");
        }
        if (renewP == null || "".equals(renewP) || !newP.equals(renewP)) {
            throw new Exception("确认密码输入错误");
        }
        // 操作
        int result;
        if (principal instanceof User) {
            result = userMapper.updatePasswordById(id, newP, version);
        } else {
            result = adminMapper.updatePasswordById(id, newP, version);
        }
        if (result != 1) {
            throw new Exception("修改失败，请重试");
        }
        // 修改存在SecurityUtils中的对象
        if (principal instanceof User) {
            User newUser = userMapper.selectOneUserByUserName(((User) principal).getUserName());
            ((User) principal).setUserPassword(newUser.getUserPassword());
            ((User) principal).setVersion(newUser.getVersion());
        } else {
            Admin newAdmin = adminMapper.selectOneAdminByAdminName(((Admin) principal).getAdminName());
            ((Admin) principal).setAdminPassword(newAdmin.getAdminPassword());
            ((Admin) principal).setVersion(newAdmin.getVersion());
        }
    }

    /**
     * 获取今天、前面3天、后面3天的日期，以及那个日期被借用的次数
     * @return 包含dateList日期数列、countList数量数列
     */
    public Map<String, Object> collectDate() {
        Map<String, Object> result = new HashMap<>();
        List<String> dateList = new ArrayList<>();
        for (int i = -3; i < 4; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            Date time = calendar.getTime();
            dateList.add(new SimpleDateFormat("M月d日").format(time));
        }
        List<Integer> countList = historyMapper.selectSucceedNearThreeDays();
        result.put("dateList", dateList);
        result.put("countList", countList);
        return result;
    }
}
