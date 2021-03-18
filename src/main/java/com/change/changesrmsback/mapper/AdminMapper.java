package com.change.changesrmsback.mapper;

import com.change.changesrmsback.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据访问层，对接数据库Admin表
 * @author Change
 */
@Mapper
public interface AdminMapper {

    /**
     * 向Admin表中插入一行
     * @param admin 要求至少包含id，adminName，adminPassword，role
     * @return 返回改变的表的行数，正常情况下为1
     */
    int insertOneAdmin(Admin admin);

    /**
     * 根据adminName查询一行数据
     * @param adminName 要查询的adminName
     * @return 返回查询到的一整个Admin对象
     */
    Admin selectOneAdminByAdminName(String adminName);
}
