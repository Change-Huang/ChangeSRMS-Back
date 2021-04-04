package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.SecondCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 第二次审核功能的控制器
 * @author Change
 */
@RestController
@RequestMapping("/secondCheck")
public class SecondCheckController {

    /** 第一次审核的功能业务 */
    private SecondCheckService secondCheckService;

    /** 第一次审核的功能业务注入 */
    @Autowired
    public void setSecondCheckService(SecondCheckService secondCheckService) {
        this.secondCheckService = secondCheckService;
    }

    /**
     * 查看第二次审核，需要审核的列表
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --pageNum，分页查询的页码，为空则查询所有<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --pageSize，分页查询中每一页的数量，为空则查询所有
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，查询成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，查询失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --siteList，查询的结果，需要审核的列表<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --total，此次查询的总数
     */
    @RequestMapping("/secondCheckList")
    public Map<String, Object> secondCheckList(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String, Object> data;
        try {
            // 取值和封装
            Page page = new Page();
            page.setPageNum((int) requestMap.get("pageNum"));
            page.setPageSize((int) requestMap.get("pageSize"));
            // 传值和操作
            data = secondCheckService.getSecondCheckList(page);
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
     * 对审核结果的提交
     * @param requestMap 前端传入json数据自动转化，要求包括<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --id，提交审核对应的history项目的id<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --isPass，是否通过，要求为bool值<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *                   --version，数据库的乐观锁标识，防止修改时冲突
     * @return status：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --200，提交成功<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --400，提交失败<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --空
     */
    @RequestMapping("/secondCheckSubmit")
    public Map<String, Object> secondCheckSubmit(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            // 取值和封装
            Long id = Long.parseLong((String) requestMap.get("id"));
            boolean isPass = (boolean) requestMap.get("isPass");
            int version = (int) requestMap.get("version");
            // 传值和操作
            secondCheckService.secondCheckSubmit(id, isPass, version);
        } catch (Exception e) {
            // 结果的封装和返回
            responseMessage.setStatus(400);
            responseMessage.setMsg("审核提交失败");
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("审核提交成功");
        return responseMessage.toMap();
    }
}
