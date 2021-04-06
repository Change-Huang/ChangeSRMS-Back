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
 * 管理员钥匙管理的
 * @author Change
 */
@Service
public class KeyManageService {

    /** 借用历史表的数据接口 */
    private HistoryMapper historyMapper;

    /** 借用历史表的数据接口注入 */
    @Autowired
    public void setHistoryMapper(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    /**
     * 获取要管理的钥匙列表
     * @param page 分页
     * @return 查询到的列表
     */
    public Map<String, Object> getKeyManageList(Page page) {
        if (page.getPageNum() == 0 || page.getPageSize() == 0) {
            page.setPageNum(null);
            page.setPageSize(null);
        } else {
            page.setPageStart((page.getPageNum() - 1) * page.getPageSize());
        }
        // 查询
        List<History> keyManageList = historyMapper.selectKeyManageList(page);
        // 总数
        int total = historyMapper.selectKeyManageCount();
        // 封装和返回
        Map<String, Object> result = new HashMap<>();
        result.put("keyManageList", keyManageList);
        result.put("total", total);
        return result;
    }

    /**
     * 借用钥匙，改变数据库的内容
     * @param id      要改变的id
     * @param version 乐观锁
     * @throws Exception 提交失败抛出异常
     */
    public void loanKey(Long id, int version) throws Exception {
        History history = new History();
        history.setId(id);
        history.setLoanKeyId(((Admin) SecurityUtils.getSubject().getPrincipal()).getId());
        history.setVersion(version);
        int result = historyMapper.loanKey(history);
        if (result != 1) {
            throw new Exception("提交失败，请重试");
        }
    }

    /**
     * 归还钥匙，改变数据库的内容
     * @param id      要改变的id
     * @param version 乐观锁
     * @throws Exception 提交失败的异常
     */
    public void returnKey(Long id, int version) throws Exception {
        History history = new History();
        history.setId(id);
        history.setReturnKeyId(((Admin) SecurityUtils.getSubject().getPrincipal()).getId());
        history.setVersion(version);
        int result = historyMapper.returnKey(history);
        if (result != 1) {
            throw new Exception("提交失败，请重试");
        }
    }
}
