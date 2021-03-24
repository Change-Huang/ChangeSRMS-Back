package com.change.changesrmsback.entity;

import lombok.Data;

/**
 * 分页实体类
 * @author Change
 */
@Data
public class Page {
    /** 当前页码 */
    private Integer pageNum;
    /** 每页数量 */
    private Integer pageSize;
    /** 起始行数 */
    private Integer pageStart;
}
