package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.entity.Site;
import com.change.changesrmsback.mapper.SiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Change
 */
@Service
public class SiteManageService {

    private SiteMapper siteMapper;

    @Autowired
    public void setSiteMapper(SiteMapper siteMapper) {
        this.siteMapper = siteMapper;
    }

    public Map<String, Object> siteList(Page page, String quary) {
        page.setPageStart((page.getPageNum() - 1) * page.getPageSize());
        List<Site> siteList = siteMapper.selectSiteList(page, "%" + quary + "%");
        int total = siteMapper.selectSiteCount("%" + quary + "%");
        Map<String, Object> result = new HashMap<>();
        result.put("siteList", siteList);
        result.put("total", total);
        return result;
    }

}
