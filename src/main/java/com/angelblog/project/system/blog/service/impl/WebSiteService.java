package com.angelblog.project.system.blog.service.impl;

import com.angelblog.project.system.blog.mapper.WebSiteMapper;
import com.angelblog.project.system.blog.service.IWebSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebSiteService implements IWebSiteService {
    @Autowired
    private WebSiteMapper webSiteMapper;
    @Override
    public Map getSiteInfo() {
        return webSiteMapper.getSiteInfo();
    }
}
