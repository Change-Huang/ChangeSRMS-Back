package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户管理功能控制器
 * @author Change
 */
@RestController
@RequestMapping("/userManage")
public class UserManageController {

    /** 用户管理业务逻辑 */
    private UserManageService userManageService;

    /** 用户管理业务逻辑自动注入 */
    @Autowired
    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    /**
     * 获取用户列表接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --query，要模糊查询的的内容，为空则查询所有<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --pageNum，分页查询的页码，为空则查询所有<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --pageSize，分页查询中每一页的数量，为空则查询所有
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，查询成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，查询失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --userList，查询的结果，用户列表<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --total，此次查询的总数
     */
    @RequestMapping("/userList")
    public Map<String, Object> userList(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String, Object> data;
        try {
            // 取值和封装
            Page page = new Page();
            String query = (String) requestMap.get("query");
            page.setPageNum((int) requestMap.get("pageNum"));
            page.setPageSize((int) requestMap.get("pageSize"));
            // 传值和操作
            data = userManageService.userList(page, query);
        } catch (Exception e) {
            // 结果的封装和返回
            responseMessage.setStatus(400);
            responseMessage.setMsg("数据请求失败");
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("数据请求成功");
        responseMessage.setData(data);
        return responseMessage.toMap();
    }

    /**
     * 用户管理时编辑用户接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，要修改的用户id，应为已经存在数据库的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --userNickname，用户姓名，不能为空，最多12个字符<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，添加成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，添加失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/editUser")
    public Map<String, Object> editUser(@RequestBody Map<String, Object> requestMap) {
        // 取值和封装
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            User user = new User();
            user.setId(Long.parseLong((String) requestMap.get("id")));
            user.setUserNickname((String) requestMap.get("userNickname"));
            user.setVersion((Integer) requestMap.get("version"));
            // 操作
            userManageService.editUser(user);
        } catch (Exception e) {
            // 封装和返回
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("修改成功");
        return responseMessage.toMap();
    }

    /**
     * 用户管理时删除用户接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，要删除的用户id，应为已经存在数据库的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，添加成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，添加失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/deleteUser")
    public Map<String, Object> deleteUser(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            Long id = Long.parseLong((String) requestMap.get("id"));
            int version = (Integer) requestMap.get("version");
            // 操作
            userManageService.deleteUser(id, version);
            // todo 删除学生之后要删除对应的history
        } catch (Exception e) {
            // 封装和返回
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("删除成功");
        return responseMessage.toMap();
    }
}
