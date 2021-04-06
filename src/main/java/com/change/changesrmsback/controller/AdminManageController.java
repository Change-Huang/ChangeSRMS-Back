package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.Admin;
import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.AdminManageService;
import com.change.changesrmsback.utils.RoleTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 管理员管理器的控制器
 * @author Change
 */
@RestController
@RequestMapping("/adminManage")
public class AdminManageController {

    /** 管理员管理业务逻辑 */
    private AdminManageService adminManageService;

    /** 管理员管理业务逻辑自动注入 */
    @Autowired
    public void setAdminManageService(AdminManageService adminManageService) {
        this.adminManageService = adminManageService;
    }

    /**
     * 管理员管理时请求管理员列表接口
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
     *         --siteList，查询的结果，管理员列表<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --total，此次查询的总数
     */
    @RequestMapping("/adminList")
    public Map<String, Object> adminList(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleSuper())) return responseMessage.toMap();
        Map<String, Object> data;
        try {
            // 取值和封装
            String query = (String) requestMap.get("query");
            Page page = new Page();
            page.setPageNum((int) requestMap.get("pageNum"));
            page.setPageSize((int) requestMap.get("pageSize"));
            // 传值和操作
            data = adminManageService.adminList(page, query);
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
     * 管理员管理时添加管理员接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --adminName，用户名，不能为空，最多18个字符<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --adminNickname，用户姓名，不能为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --adminPassword，管理员密码，不能为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --role，管理员角色，不能为空
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，添加成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，添加失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/addAdmin")
    public Map<String, Object> addAdmin(@RequestBody Map<String, Object> requestMap) {
        // 取值和封装
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleSuper())) return responseMessage.toMap();
        try {
            Admin admin = new Admin();
            admin.setAdminName((String) requestMap.get("adminName"));
            admin.setAdminNickname((String) requestMap.get("adminNickname"));
            admin.setAdminPassword((String) requestMap.get("adminPassword"));
            admin.setRole((String) requestMap.get("role"));
            // 操作
            adminManageService.addAdmin(admin);
        } catch (Exception e) {
            // 封装和返回
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("添加成功");
        return responseMessage.toMap();
    }

    /**
     * 管理员管理时编辑管理员接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，要修改的管理员id，应为已经存在数据库的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --adminNickname，用户姓名，不能为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --role，管理员角色，不能为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，编辑成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，编辑失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/editAdmin")
    public Map<String, Object> editAdmin(@RequestBody Map<String, Object> requestMap) {
        // 取值和封装
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            Admin admin = new Admin();
            admin.setId(Long.parseLong((String) requestMap.get("id")));
            admin.setAdminNickname((String) requestMap.get("adminNickname"));
            admin.setRole((String) requestMap.get("role"));
            admin.setVersion((Integer) requestMap.get("version"));
            // 操作
            adminManageService.editAdmin(admin);
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
     * 管理员管理时删除管理员接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，要删除的管理员id，应为已经存在数据库的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，删除成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，删除失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/deleteAdmin")
    public Map<String, Object> deleteAdmin(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            Long id = Long.parseLong((String) requestMap.get("id"));
            int version = (Integer) requestMap.get("version");
            // 操作
            adminManageService.deleteAdmin(id, version);
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
