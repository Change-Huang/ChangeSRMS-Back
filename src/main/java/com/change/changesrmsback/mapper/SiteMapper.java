package com.change.changesrmsback.mapper;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.Site;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据访问层，对接数据库Site表
 * @author Change
 */
@Mapper
public interface SiteMapper {

    /**
     * 以列表的方式查询场地，支持分页和模糊，模糊查询为场地名称和位置模糊
     * @param page  可为null，若要分页，必须包含pageSize和pageStart属性
     * @param query 可为null，若要模糊查询，则不能为空
     * @return 符合条件的list列表
     */
    List<Site> selectSiteList(@Param("page") Page page, @Param("query") String query);

    int selectSiteCount(String query);
}
