package com.change.changesrmsback.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 管理员实体类
 * @author Change
 */
@Data
public class Admin {

    /** 管理员id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    /** 管理员用户名 */
    private String adminName;
    /** 管理员姓名 */
    private String adminNickname;
    /** 管理员密码 */
    private String adminPassword;
    /** 管理员的角色，general是普通管理员，super是超级管理员 */
    private String role;
    /** 乐观锁 */
    private Integer version;
}
