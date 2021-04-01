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
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String siteName;
    private String location;
    private Integer seat;
    private Boolean hasKeys;
    private Boolean isLent;
    private Integer version;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer reservationTimes;
}
