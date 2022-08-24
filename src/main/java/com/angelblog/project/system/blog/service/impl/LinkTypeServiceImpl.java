package com.angelblog.project.system.blog.service.impl;

import com.angelblog.project.system.blog.domain.LinkType;
import com.angelblog.project.system.blog.mapper.LinkTypeMapper;
import com.angelblog.project.system.blog.service.ILinkTypeService;
import com.angelblog.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 链接分类Service业务层处理
 * 
 * @author alcedo
 * @date 2020-11-26
 */
@Service
public class LinkTypeServiceImpl implements ILinkTypeService 
{
    @Autowired
    private LinkTypeMapper linkTypeMapper;

    /**
     * 查询链接分类
     * 
     * @param id 链接分类ID
     * @return 链接分类
     */
    @Override
    public LinkType selectLinkTypeById(Long id)
    {
        return linkTypeMapper.selectLinkTypeById(id);
    }

    @Override
    public LinkType selectLinkTypeByType(String type) {
        return linkTypeMapper.selectLinkTypeByType(type);
    }

    /**
     * 查询链接分类列表
     * 
     * @param linkType 链接分类
     * @return 链接分类
     */
    @Override
    public List<LinkType> selectLinkTypeList(LinkType linkType)
    {
        return linkTypeMapper.selectLinkTypeList(linkType);
    }

    /**
     * 新增链接分类
     * 
     * @param linkType 链接分类
     * @return 结果
     */
    @Override
    public int insertLinkType(LinkType linkType)
    {
        return linkTypeMapper.insertLinkType(linkType);
    }

    /**
     * 修改链接分类
     * 
     * @param linkType 链接分类
     * @return 结果
     */
    @Override
    public int updateLinkType(LinkType linkType)
    {
        return linkTypeMapper.updateLinkType(linkType);
    }

    /**
     * 删除链接分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLinkTypeByIds(String ids)
    {
        return linkTypeMapper.deleteLinkTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除链接分类信息
     * 
     * @param id 链接分类ID
     * @return 结果
     */
    @Override
    public int deleteLinkTypeById(Long id)
    {
        return linkTypeMapper.deleteLinkTypeById(id);
    }
}
