package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.Admin;
import com.change.changesrmsback.entity.History;
import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.mapper.HistoryMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第一次审核的业务逻辑
 * @author Change
 */
@Service
public class FirstCheckService {

    /** 对接数据库的history表 */
    private HistoryMapper historyMapper;

    /** 对接数据库的history表的mapper自动注入 */
    @Autowired
    public void setHistoryMapper(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    /**
     * 获取等待第一次审核的所有项目
     * @param page 分页
     * @return 得到第一次待审核的项目
     */
    public Map<String, Object> getFirstCheckList(Page page) {
        if (page.getPageNum() == 0 || page.getPageSize() == 0) {
            page.setPageNum(null);
            page.setPageSize(null);
        } else {
            page.setPageStart((page.getPageNum() - 1) * page.getPageSize());
        }
        // 查询
        List<History> firstCheckList = historyMapper.selectFirstCheck(page);
        // 总数
        int total = historyMapper.selectFirstCheckCount();
        // 封装和返回
        Map<String, Object> result = new HashMap<>();
        result.put("firstCheckList", firstCheckList);
        result.put("total", total);
        return result;
    }

    /**
     * 提交某一条目的第一次审核的结果
     * @param id      这一条目的id
     * @param isPass  是否通过
     * @param version 对应数据库的乐观锁
     * @throws Exception 管理员未登录等错误
     */
    public void firstCheckSubmit(Long id, boolean isPass, int version) throws Exception {
        // 取值
        History history = new History();
        history.setGeneralAdminId(((Admin) SecurityUtils.getSubject().getPrincipal()).getId());
        history.setId(id);
        if (isPass) {
            history.setLoanState(1);
        } else {
            history.setLoanState(3);
        }
        history.setVersion(version);
        // 封装
        int result = historyMapper.firstCheckUpdate(history);
        if (result != 1) {
            throw new Exception("提交失败，请重试");
        }
    }
}
