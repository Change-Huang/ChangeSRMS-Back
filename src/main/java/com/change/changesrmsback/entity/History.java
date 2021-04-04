package com.change.changesrmsback.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * 借用历史实体类
 * @author Change
 */
@Data
public class History {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private long siteId;
    @JsonSerialize(using = ToStringSerializer.class)
    private long userId;
    private String reason;
    private Date beginTime;
    private Date endTime;
    @JsonSerialize(using = ToStringSerializer.class)
    private long generalAdminId;
    @JsonSerialize(using = ToStringSerializer.class)
    private long superAdminId;
    @JsonSerialize(using = ToStringSerializer.class)
    private long loanKeyId;
    @JsonSerialize(using = ToStringSerializer.class)
    private long returnKeyId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int loanState;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int keyState;
    private Integer version;

    private String userNickname;
    private String adminNickname;
    private String superAdminNickname;
    private String siteName;
}
