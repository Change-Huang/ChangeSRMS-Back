package com.change.changesrmsback.mapper;

import com.change.changesrmsback.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据访问层，对接数据库User表
 * @author Change
 */
@Mapper
public interface UserMapper {

    /**
     * 向User表中插入一行
     * @param user 要求至少包含id，userName，userNickname，userPassword
     * @return 返回改变的表的行数，正常情况下为1
     */
    int insertOneUser(User user);

    /**
     * 根据userName查询一行数据
     * @param userName 要查询的userName
     * @return 返回查询到的一整个User对象
     */
    User selectOneUserByUserName(String userName);
}
