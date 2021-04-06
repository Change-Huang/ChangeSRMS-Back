package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.HistoryManageService;
import com.change.changesrmsback.utils.RoleTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户管理自己的申请历史列表相关控制器
 * @author Change
 */
@RestController
@RequestMapping("/historyManage")
public class HistoryManageController {

    /** 用户管理自己的申请历史列表业务逻辑 */
    private HistoryManageService historyManageService;

    /** 用户管理自己的申请历史列表业务逻辑自动注入 */
    @Autowired
    public void setReservationListService(HistoryManageService historyManageService) {
        this.historyManageService = historyManageService;
    }

    /**
     * 申请历史管理时请求申请列表接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
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
    @RequestMapping("/historyList")
    public Map<String, Object> historyList(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleUser())) return responseMessage.toMap();
        Map<String, Object> data;
        try {
            // 取值和封装
            Page page = new Page();
            page.setPageNum((int) requestMap.get("pageNum"));
            page.setPageSize((int) requestMap.get("pageSize"));
            // 传值和操作
            data = historyManageService.historyList(page);
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
     * 申请历史管理时删除某一申请历史接口
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
    @RequestMapping("/deleteHistory")
    public Map<String, Object> deleteHistory(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleUser())) return responseMessage.toMap();
        try {
            Long id = Long.parseLong((String) requestMap.get("id"));
            int version = (Integer) requestMap.get("version");
            // 操作
            historyManageService.deleteSite(id, version);
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
