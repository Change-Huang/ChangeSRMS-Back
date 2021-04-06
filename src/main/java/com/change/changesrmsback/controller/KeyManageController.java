package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.KeyManageService;
import com.change.changesrmsback.utils.RoleTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 管理员钥匙借还控制器
 * @author Change
 */
@RestController
@RequestMapping("/keyManage")
public class KeyManageController {

    /** 钥匙借用业务逻辑 */
    private KeyManageService keyManageService;

    /** 钥匙借用业务逻辑注入 */
    @Autowired
    public void setKeyManageService(KeyManageService keyManageService) {
        this.keyManageService = keyManageService;
    }

    /**
     * 钥匙管理时获取所有需要管理的钥匙的接口
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --pageNum，分页查询的页码，为空则查询所有<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --pageSize，分页查询中每一页的数量，为空则查询所有
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，查询成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，查询失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --keyManageList，查询的结果，要管理的钥匙列表<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --total，此次查询的总数
     */
    @RequestMapping("/keyManageList")
    public Map<String, Object> keyManageList(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleGeneral())) return responseMessage.toMap();
        Map<String, Object> data;
        try {
            // 取值和封装
            Page page = new Page();
            page.setPageNum((int) requestMap.get("pageNum"));
            page.setPageSize((int) requestMap.get("pageSize"));
            // 传值和操作
            data = keyManageService.getKeyManageList(page);
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
     * 借出钥匙的数据提交
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，提交审核对应的history项目的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，提交成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，提交失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/loanKey")
    public Map<String, Object> loanKey(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleGeneral())) return responseMessage.toMap();
        try {
            Long id = Long.parseLong((String) requestMap.get("id"));
            int version = (Integer) requestMap.get("version");
            // 操作
            keyManageService.loanKey(id, version);
        } catch (Exception e) {
            // 封装和返回
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("借出成功");
        return responseMessage.toMap();
    }

    /**
     * 归还钥匙的数据提交
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，提交审核对应的history项目的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，提交成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，提交失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/returnKey")
    public Map<String, Object> returnKey(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (responseMessage.getOneResponseMessage(RoleTest.hasRoleGeneral())) return responseMessage.toMap();
        try {
            Long id = Long.parseLong((String) requestMap.get("id"));
            int version = (Integer) requestMap.get("version");
            // 操作
            keyManageService.returnKey(id, version);
        } catch (Exception e) {
            // 封装和返回
            responseMessage.setStatus(400);
            responseMessage.setMsg(e.getMessage());
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("归还成功");
        return responseMessage.toMap();
    }
}
