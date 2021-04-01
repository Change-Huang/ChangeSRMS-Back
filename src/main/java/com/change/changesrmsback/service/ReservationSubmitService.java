package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.History;
import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.Site;
import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.mapper.HistoryMapper;
import com.change.changesrmsback.mapper.SiteMapper;
import com.change.changesrmsback.utils.CommonUtils;
import com.change.changesrmsback.utils.SnowflakeIdWorker;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户申请借用场地相关业务逻辑
 * @author Change
 */
@Service
public class ReservationSubmitService {

    /** 场地数据的数据访问层 */
    private SiteMapper siteMapper;

    /** 借用历史的数据访问层 */
    private HistoryMapper historyMapper;

    /** 场地数据的数据访问层注入 */
    @Autowired
    public void setSiteMapper(SiteMapper siteMapper) {
        this.siteMapper = siteMapper;
    }

    /** 借用历史的数据访问层注入 */
    @Autowired
    public void setHistoryMapper(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    /**
     * 查询场地列表，可选择进行分页或者模糊查询，模糊查询只模糊名称siteName和位置location
     * @param page  如果选择分页，那么pageNum和pageSize属性不能为空
     * @param query 如果选择模糊查询，可传入，若为空则不模糊
     * @return 将查询的结果封装进map，包括siteList场地列表和总数total
     */
    public Map<String, Object> siteList(Page page, String query) {
        // 计算查询分行的起始
        if (page.getPageNum() == 0 || page.getPageSize() == 0) {
            page.setPageNum(null);
            page.setPageSize(null);
        } else {
            page.setPageStart((page.getPageNum() - 1) * page.getPageSize());
        }
        // 整合模糊查询的通配符%，查询
        List<Site> siteList = siteMapper.selectSiteListForReservation(page, "%" + query + "%");
        // 总数
        int total = siteMapper.selectSiteCount("%" + query + "%");
        // 封装和返回
        Map<String, Object> result = new HashMap<>();
        result.put("siteList", siteList);
        result.put("total", total);
        return result;
    }

    /**
     * 用户提交场地借用申请<br>
     * 将开始和结束时间格式化为date类，并添加用户id和此次借用历史的id
     * @param begin  借用的开始时间，格式为“yyyy-MM-dd hh-mm”
     * @param end    借用的结束时间，格式为“yyyy-MM-dd hh-mm”
     * @param reason 借用原因
     * @param siteId 借用的场地id
     * @throws Exception 数据格式不对或操作数据库错误等等
     */
    public void submitReservation(String begin, String end, String reason, Long siteId) throws Exception {
        History history = new History();
        history.setBeginTime(CommonUtils.formateDate(begin + ":00"));
        history.setEndTime(CommonUtils.formateDate(end + ":00"));
        history.setReason(reason);
        history.setSiteId(siteId);
        history.setUserId(((User) SecurityUtils.getSubject().getPrincipal()).getId());
        history.setId(new SnowflakeIdWorker(0, 0).nextId());
        int result = historyMapper.insertOneHistory(history);
        if (result != 1) {
            throw new Exception("添加失败，请重试");
        }
    }
}
