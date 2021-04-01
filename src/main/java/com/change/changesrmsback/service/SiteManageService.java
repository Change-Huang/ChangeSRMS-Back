package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.Site;
import com.change.changesrmsback.mapper.SiteMapper;
import com.change.changesrmsback.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场地管理业务逻辑
 * @author Change
 */
@Service
public class SiteManageService {

    /** 场地数据的数据访问层 */
    private SiteMapper siteMapper;

    /** 场地数据的数据访问层注入 */
    @Autowired
    public void setSiteMapper(SiteMapper siteMapper) {
        this.siteMapper = siteMapper;
    }

    /**
     * 查询场地列表，可选择进行分页或者模糊查询，模糊查询只模糊名称siteName和位置location
     * @param page  如果选择分页，那么pageNum和pageSize属性不能为空
     * @param query 如果选择模糊查询，可传入，若为空则不模糊
     * @return 将查询的结果封装进map，包括siteList场地列表和总数total
     */
    public Map<String, Object> siteList(Page page, String query) {
        // 计算查询分行的起始
        if (page.getPageNum() == 0 || page.getPageSize() == 0) {
            page.setPageNum(null);
            page.setPageSize(null);
        } else {
            page.setPageStart((page.getPageNum() - 1) * page.getPageSize());
        }
        // 整合模糊查询的通配符%，查询
        List<Site> siteList = siteMapper.selectSiteList(page, "%" + query + "%");
        // 总数
        int total = siteMapper.selectSiteCount("%" + query + "%");
        // 封装和返回
        Map<String, Object> result = new HashMap<>();
        result.put("siteList", siteList);
        result.put("total", total);
        return result;
    }

    /**
     * 添加site的方法，会使用雪花算法生成id
     * @param site 至少包括siteName、seat和hasKeys
     * @throws Exception 传入的数据有误时抛出异常，操作数据库失败时抛出异常
     */
    public void addSite(Site site) throws Exception {
        // 数据验证
        if (site.getSiteName() == null || "".equals(site.getSiteName())) {
            throw new Exception("场地名称不能为空");
        }
        if (site.getSiteName().length() > 18) {
            throw new Exception("场地名称长度不多余18个字符");
        }
        if (site.getSeat() == null) {
            throw new Exception("容纳的人数不能为空");
        }
        if (site.getHasKeys() == null) {
            throw new Exception("是否包含场地钥匙不能为空");
        }
        // 添加id
        site.setId(new SnowflakeIdWorker(0, 0).nextId());
        int result = siteMapper.insertOneSite(site);
        if (result != 1) {
            throw new Exception("添加失败，请重试");
        }
    }

    /**
     * 编辑site的方法
     * @param site 至少包括id、siteName、seat和hasKeys、version
     * @throws Exception 传入的数据有误时抛出异常，操作数据库失败时抛出异常
     */
    public void editSite(Site site) throws Exception {
        // 数据验证
        if (site.getId() == 0) {
            throw new Exception("id不能为空");
        }
        if (site.getSiteName() == null || "".equals(site.getSiteName())) {
            throw new Exception("场地名称不能为空");
        }
        if (site.getSeat() == null) {
            throw new Exception("容纳的人数不能为空");
        }
        if (site.getSiteName().length() > 18) {
            throw new Exception("场地名称长度不多于18个字符");
        }
        if (site.getHasKeys() == null) {
            throw new Exception("是否包含场地钥匙不能为空");
        }
        // 操作
        int result = siteMapper.updateOneSite(site);
        if (result != 1) {
            throw new Exception("添加失败，请重试");
        }
    }

    /**
     * 删除site的方法
     * @param id      要删除的site的id
     * @param version 数据库的乐观锁标识，防止修改时冲突
     * @throws Exception 传入的id有误时抛出异常，操作数据库失败时抛出异常
     */
    public void deleteSite(Long id, int version) throws Exception {
        // 数据验证
        if (id == 0) {
            throw new Exception("id不能为空");
        }
        // 操作
        int result = siteMapper.deleteOneSite(id, version);
        if (result != 1) {
            throw new Exception("删除失败，请重试");
        }
    }
}
