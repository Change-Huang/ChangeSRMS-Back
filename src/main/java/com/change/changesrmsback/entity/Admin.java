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
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String adminName;
    private String adminNickname;
    private String adminPassword;
    private String role;
    private Integer version;
}
