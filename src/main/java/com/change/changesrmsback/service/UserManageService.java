package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理逻辑业务层
 * @author Change
 */
@Service
public class UserManageService {

    /** 用户user数据库 */
    private UserMapper userMapper;

    /** 用户user数据库的自动注入 */
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 查询用户列表，可选择进行分页或者模糊查询，模糊查询只模糊名称siteName和位置location
     * @param page  如果选择分页，那么pageNum和pageSize属性不能为空
     * @param query 如果选择模糊查询，可传入，若为空则不模糊
     * @return 将查询的结果封装进map，包括userList场地列表和总数total
     */
    public Map<String, Object> userList(Page page, String query) {
        // 计算查询分行的起始
        page.setPageStart((page.getPageNum() - 1) * page.getPageSize());
        // 整合模糊查询的通配符%，查询
        List<User> siteList = userMapper.selectUserList(page, "%" + query + "%");
        // 总数
        int total = userMapper.selectUserCount("%" + query + "%");
        // 封装和返回
        Map<String, Object> result = new HashMap<>();
        result.put("userList", siteList);
        result.put("total", total);
        return result;
    }

    /**
     * 编辑site的方法
     * @param user 至少包括id、siteNickname、version
     * @throws Exception 传入的数据有误时抛出异常，操作数据库失败时抛出异常
     */
    public void editUser(User user) throws Exception {
        // 数据验证
        if (user.getId() == 0) {
            throw new Exception("id不能为空");
        }
        if (user.getUserNickname() == null || "".equals(user.getUserNickname())) {
            throw new Exception("姓名不能为空");
        }
        if (user.getUserNickname().length() > 12) {
            throw new Exception("姓名长度不多于18个字符");
        }
        // 操作
        int result = userMapper.updateOneUser(user);
        if (result != 1) {
            throw new Exception("添加失败，请重试");
        }
    }

    /**
     * 删除user的方法
     * @param id      要删除的user的id
     * @param version 数据库的乐观锁标识，防止修改时冲突
     * @throws Exception 传入的id有误时抛出异常，操作数据库失败时抛出异常
     */
    public void deleteUser(Long id, int version) throws Exception {
        // 数据验证
        if (id == 0) {
            throw new Exception("id不能为空");
        }
        // 操作
        int result = userMapper.deleteOneUser(id, version);
        if (result != 1) {
            throw new Exception("删除失败，请重试");
        }
    }
}
