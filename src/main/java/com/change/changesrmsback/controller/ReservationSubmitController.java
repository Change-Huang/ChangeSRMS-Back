package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.ReservationSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户申请借用场地相关控制器
 * @author Change
 */
@RestController
@RequestMapping("/reservationSubmit")
public class ReservationSubmitController {

    /** 用户申请借用场地的业务逻辑 */
    private ReservationSubmitService reservationSubmitService;

    /** 用户申请借用场地的业务逻辑注入 */
    @Autowired
    public void setReservationSubmitService(ReservationSubmitService reservationSubmitService) {
        this.reservationSubmitService = reservationSubmitService;
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
        Map<String, Object> data;
        try {
            // 取值和封装
            String query = (String) requestMap.get("query");
            Page page = new Page();
            page.setPageNum((int) requestMap.get("pageNum"));
            page.setPageSize((int) requestMap.get("pageSize"));
            // 传值和操作
            data = reservationSubmitService.siteList(page, query);
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

    @RequestMapping("/submit")
    public Map<String, Object> submit(@RequestBody Map<String, Object> requestMap) {
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String, Object> data;
        try {
            // 取值和封装
            String begin = (String) requestMap.get("begin");
            String end = (String) requestMap.get("end");
            String reason = (String) requestMap.get("reason");
            Long siteId = Long.parseLong((String) requestMap.get("siteId"));
            // 传值和操作
            reservationSubmitService.submitReservation(begin, end, reason, siteId);
        } catch (Exception e) {
            // 结果的封装和返回
            responseMessage.setStatus(400);
            responseMessage.setMsg("申请提交失败");
            return responseMessage.toMap();
        }
        responseMessage.setStatus(200);
        responseMessage.setMsg("申请提交成功");
        return responseMessage.toMap();
    }
}
