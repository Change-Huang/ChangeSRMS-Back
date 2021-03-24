package com.change.changesrmsback.entity;

import lombok.Data;

/**
 * 系统用户
 * @author Change
 */
@Data
public class User {
    private long id;
    private String userName;
    private String userNickname;
    private String userPassword;
    private Integer version;
}
