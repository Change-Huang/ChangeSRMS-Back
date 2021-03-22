package com.change.changesrmsback.entity;

import lombok.Data;

/**
 * 场地实体类
 * @author Change
 */
@Data
public class Site {
    private long id;
    private String siteName;
    private Boolean hasKeys;
    private int version;
}
