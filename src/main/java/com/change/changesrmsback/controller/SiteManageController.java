package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.entity.Site;
import com.change.changesrmsback.service.SiteManageService;
import com.change.changesrmsback.utils.RoleTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 场地管理控制器
 * @author Change
 */
@RestController
@RequestMapping("/siteManage")
public class SiteManageController {

    /** 场地管理操作业务逻辑 */
    private SiteManageService siteManageService;

    /** 场地管理操作业务逻辑的注入 */
    @Autowired
    public void setSiteManageService(SiteManageService siteManageService) {
        this.siteManageService = siteManageService;
    }

    /**
     * 场地管理时请求场地列表接口
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
     *         --siteList，查询的结果，场地列表<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --total，此次查询的总数
     */
    @RequestMapping("/siteList")
    public Map<String, Object> siteList(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleGeneral())) return responseMessage.toMap();
        Map<String, Object> data;
        try {
            // 取值和封装
            Page page = new Page();
            page.setPageNum((int) requestMap.get("pageNum"));
            page.setPageSize((int) requestMap.get("pageSize"));
            String query = (String) requestMap.get("query");
            // 传值和操作
            data = siteManageService.siteList(page, query);
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
     * 场地管理时添加场地接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --siteName，场地名称，不能为空，最多18个字符<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --location，场地的地址或位置，可为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --seat，场地能容纳的人数，不能为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --hasKeys，场地借用是否需要钥匙，不能为空
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，添加成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，添加失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/addSite")
    public Map<String, Object> addSite(@RequestBody Map<String, Object> requestMap) {
        // 取值和封装
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleGeneral())) return responseMessage.toMap();
        try {
            Site site = new Site();
            site.setSiteName((String) requestMap.get("siteName"));
            site.setLocation((String) requestMap.get("location"));
            site.setSeat((Integer) requestMap.get("seat"));
            site.setHasKeys((Boolean) requestMap.get("hasKeys"));
            // 操作
            siteManageService.addSite(site);
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
     * 场地管理时编辑场地接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，要修改的场地id，应为已经存在数据库的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --siteName，场地名称，不能为空，最多18个字符<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --location，场地的地址或位置，可为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --seat，场地能容纳的人数，不能为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --hasKeys，场地借用是否需要钥匙，不能为空<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，编辑成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，编辑失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/editSite")
    public Map<String, Object> editSite(@RequestBody Map<String, Object> requestMap) {
        // 取值和封装
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleGeneral())) return responseMessage.toMap();
        try {
            Site site = new Site();
            site.setId(Long.parseLong((String) requestMap.get("id")));
            site.setSiteName((String) requestMap.get("siteName"));
            site.setLocation((String) requestMap.get("location"));
            site.setSeat((Integer) requestMap.get("seat"));
            site.setHasKeys((Boolean) requestMap.get("hasKeys"));
            site.setVersion((Integer) requestMap.get("version"));
            // 操作
            siteManageService.editSite(site);
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
     * 场地管理时删除场地接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，要删除的场地id，应为已经存在数据库的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，删除成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，删除失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/deleteSite")
    public Map<String, Object> deleteSite(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleGeneral())) return responseMessage.toMap();
        try {
            int version = (Integer) requestMap.get("version");
            Long id = Long.parseLong((String) requestMap.get("id"));
            // 操作
            siteManageService.deleteSite(id, version);
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
