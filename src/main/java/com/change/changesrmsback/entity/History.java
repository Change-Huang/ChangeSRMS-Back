package com.change.changesrmsback.entity;

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
    private long siteId;
    private long userId;
    private String reason;
    private Date beginTime;
    private Date endTime;
    private long generalAdminId;
    private long superAdminId;
    private long loanKeyId;
    private long returnKeyId;
    private Integer version;
}
