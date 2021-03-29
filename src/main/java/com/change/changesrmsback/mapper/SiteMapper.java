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

    /**
     * 查询场地目前的总数，支持模糊查询
     * @param query 可为null，若要模糊查询，则不能为空
     * @return 符合条件的场地数目
     */
    int selectSiteCount(String query);

    /**
     * 插入一个场地
     * @param site 要插入的场地，包括id、siteName、location、seat、hasKeys
     * @return 成功返回1，不成功返回0
     */
    int insertOneSite(Site site);

    /**
     * 修改一个场地
     * @param site 要修改的场地，包括id、siteName、location、seat、hasKeys、version
     * @return 成功返回1，不成功返回0
     */
    int updateOneSite(Site site);

    /**
     * 删除一个场地
     * @param id      要删除的场地的id
     * @param version 数据库的乐观锁标识，防止修改时冲突
     * @return 成功返回1，不成功返回0
     */
    int deleteOneSite(@Param("id") Long id, @Param("version") int version);
}
