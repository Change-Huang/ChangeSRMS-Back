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

    /** 借用历史的id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    /** 要借用的场地的id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long siteId;
    /** 借用者的id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long userId;
    /** 借用原因 */
    private String reason;
    /** 借用开始时间 */
    private Date beginTime;
    /** 借用结束时间 */
    private Date endTime;
    /** 初审管理员id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long generalAdminId;
    /** 二审管理员id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long superAdminId;
    /** 借出钥匙的管理员id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long loanKeyId;
    /** 归还钥匙的管理员id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long returnKeyId;
    /** 借出的状态 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int loanState;
    /** 钥匙的借出状态 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int keyState;
    /** 乐观锁 */
    private Integer version;


    /** 用户的名称 */
    private String userNickname;
    /** 管理员的名称 */
    private String adminNickname;
    /** 二审管理员的名称 */
    private String superAdminNickname;
    /** 借出钥匙的管理员名称 */
    private String loanKeyAdmin;
    /** 归还钥匙的管理员名称 */
    private String returnKeyAdmin;
    /** 场地名称 */
    private String siteName;
}
