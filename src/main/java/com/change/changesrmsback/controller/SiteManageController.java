package com.change.changesrmsback.controller;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.ResponseMessage;
import com.change.changesrmsback.service.SiteManageService;
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

    private SiteManageService siteManageService;

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
     *         --200，查询成功<br>
     *         msg：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --状态码解释信息<br>
     *         data：<br>&nbsp;&nbsp;&nbsp;&nbsp;
     *         --siteList，查询的结果，场地列表
     *         --total，此次查询的总数
     */
    @RequestMapping("/siteList")
    public Map<String, Object> siteList(@RequestBody Map<String, Object> requestMap) {
        String query = (String) requestMap.get("query");
        Page page = new Page();
        page.setPageNum((int) requestMap.get("pageNum"));
        page.setPageSize((int) requestMap.get("pageSize"));
        Map<String, Object> data = siteManageService.siteList(page, query);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(200);
        responseMessage.setMsg("数据请求成功");
        responseMessage.setData(data);
        return responseMessage.toMap();
    }

    @RequestMapping("/addSite")
    public Map<String, Object> addSite(@RequestBody Map<String, Object> requestMap) {
        for (String key : requestMap.keySet()) {
            System.out.println(key + " : " + requestMap.get(key));
        }
        return null;
    }
}
