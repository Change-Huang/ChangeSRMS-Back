package com.change.changesrmsback.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 系统用户
 * @author Change
 */
@Data
public class User {

    /** 用户的id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    /** 用户的用户名 */
    private String userName;
    /** 用户的姓名 */
    private String userNickname;
    /** 用户的密码 */
    private String userPassword;
    /** 乐观锁 */
    private Integer version;
    /** 用户借用的次数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer useSum;
}
