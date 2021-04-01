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
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String userName;
    private String userNickname;
    private String userPassword;
    private Integer version;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer useSum;
}
