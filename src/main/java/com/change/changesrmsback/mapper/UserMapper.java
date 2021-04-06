package com.change.changesrmsback.mapper;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 修改密码
     * @param id          要修改密码的id
     * @param newPassword 新的密码
     * @param version     乐观锁
     * @return 成功返回1，失败返回0
     */
    int updatePasswordById(@Param("id") Long id, @Param("password") String newPassword, @Param("version") int version);

    /**
     * 分页查询用户的列表
     * @param page  分页，可为空
     * @param query 模糊查询，将模糊姓名和用户名
     * @return 用户列表
     */
    List<User> selectUserList(@Param("page") Page page, @Param("query") String query);

    /**
     * 分页查询用户的列表的数量
     * @param query 模糊查询，将模糊姓名和用户名
     * @return 查询得到的结果
     */
    int selectUserCount(String query);

    /**
     * 修改一个用户，只能修改nickname
     * @param user 包括nickname、version、id
     * @return 成功返回1，失败返回0
     */
    int updateOneUser(User user);

    /**
     * 删除一个用户
     * @param id      要删除的id
     * @param version 乐观锁
     * @return 成功返回1，失败返回0
     */
    int deleteOneUser(@Param("id") Long id, @Param("version") int version);
}
