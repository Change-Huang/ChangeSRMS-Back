package com.change.changesrmsback.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 场地实体类
 * @author Change
 */
@Data
public class Site {

    /** 场地的id */
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    /** 场地名称 */
    private String siteName;
    /** 场地所在的地址 */
    private String location;
    /** 场地能容纳的座位数 */
    private Integer seat;
    /** 场地是否需要钥匙 */
    private Boolean hasKeys;
    /** 场地是否已经被借出 */
    private Boolean isLent;
    /** 乐观锁 */
    private Integer version;
    /** 场地被借用过的次数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer reservationTimes;
}
