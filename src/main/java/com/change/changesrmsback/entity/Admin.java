package com.change.changesrmsback.entity;

import lombok.Data;

/**
 * 管理员实体类
 * @author Change
 */
@Data
public class Admin {
    private long id;
    private String adminName;
    private String adminNickname;
    private String adminPassword;
    private String role;
    private Integer version;
}
