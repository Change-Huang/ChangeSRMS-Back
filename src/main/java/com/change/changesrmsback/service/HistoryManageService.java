package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.History;
import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.mapper.HistoryMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请历史管理的业务逻辑
 * @author Change
 */
@Service
public class HistoryManageService {

    /** 对数据库history表的操作 */
    private HistoryMapper historyMapper;

    /** 对数据库history表的操作注入 */
    @Autowired
    public void setHistoryMapper(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    /**
     * 分页查询这一用户所申请的历史列表，需要从安全管理框架中
     * @param page 如果选择分页，那么pageNum和pageSize属性不能为空
     * @return 查询到的列表结果
     */
    public Map<String, Object> historyList(Page page) {
        // 计算查询分行的起始
        if (page.getPageNum() == 0 || page.getPageSize() == 0) {
            page.setPageNum(null);
            page.setPageSize(null);
        } else {
            page.setPageStart((page.getPageNum() - 1) * page.getPageSize());
        }
        long userId = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
        // 整合模糊查询的通配符%，查询
        List<History> historyList = historyMapper.selectHistoryListByUserId(page, userId);
        // 总数
        int total = historyMapper.selectHistoryCountByUserId(userId);
        // 封装和返回
        Map<String, Object> result = new HashMap<>();
        result.put("historyList", historyList);
        result.put("total", total);
        return result;
    }

    /**
     * 删除history的方法
     * @param id      要删除的history的id
     * @param version 数据库的乐观锁标识，防止修改时冲突
     * @throws Exception 传入的id有误时抛出异常，操作数据库失败时抛出异常
     */
    public void deleteHistory(Long id, int version) throws Exception {
        // 数据验证
        if (id == 0) {
            throw new Exception("id不能为空");
        }
        History history = historyMapper.selectOneHistory(id);
        if (history.getLoanState() == 2 || history.getLoanState() == 3) {
            throw new Exception("不能删除审核完的申请");
        }
        // 操作
        int result = historyMapper.deleteOneHistory(id, version);
        if (result != 1) {
            throw new Exception("删除失败，请重试");
        }
    }
}
