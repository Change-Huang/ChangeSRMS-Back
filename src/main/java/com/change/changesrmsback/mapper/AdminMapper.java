package com.change.changesrmsback.mapper;

import com.change.changesrmsback.entity.Admin;
import com.change.changesrmsback.entity.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据访问层，对接数据库Admin表
 * @author Change
 */
@Mapper
public interface AdminMapper {

    /**
     * 向Admin表中插入一行
     * @param admin 要求至少包含id，adminName，adminNickname，adminPassword，role
     * @return 返回改变的表的行数，正常情况下为1
     */
    int insertOneAdmin(Admin admin);

    /**
     * 根据adminName查询一行数据
     * @param adminName 要查询的adminName
     * @return 返回查询到的一整个Admin对象
     */
    Admin selectOneAdminByAdminName(String adminName);

    /**
     * 根据传入的id修改其password
     * @param id          要修改的admin的id
     * @param newPassword 新的密码
     * @param version     乐观锁
     * @return 返回修改的行数，一般返回1为成功
     */
    int updatePasswordById(@Param("id") Long id, @Param("password") String newPassword, @Param("version") int version);

    /**
     * 查询管理员列表，可分页查询或者模糊查询
     * @param page  分页，可为空
     * @param query 模糊查询，可为空
     * @return 返回查询结果
     */
    List<Admin> selectAdminList(@Param("page") Page page, @Param("query") String query);

    /**
     * 查询列表的总数，可模糊查询
     * @param query 模糊查询，可为空
     * @return 查询到的数量
     */
    int selectAdminCount(String query);

    /**
     * 修改某一行的admin，支持修改nickname和role
     * @param admin 必须包括version和要修改的id，以及nickname和role
     * @return 修改成功返回1
     */
    int updateOneAdmin(Admin admin);

    /**
     * 删除某一行admin
     * @param id      要删除的那一行的id
     * @param version 乐观锁
     * @return 删除成功返回1
     */
    int deleteOneAdmin(@Param("id") Long id, @Param("version") int version);
}
