package com.change.changesrmsback.mapper;

import com.change.changesrmsback.entity.History;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据访问层，对接数据库history表
 * @author Change
 */
@Mapper
public interface HistoryMapper {

    /**
     * 插入一个历史
     * @param history 要插入的场地，包括id、siteName、location、seat、hasKeys
     * @return 成功返回1，不成功返回0
     */
    int insertOneHistory(History history);
}
