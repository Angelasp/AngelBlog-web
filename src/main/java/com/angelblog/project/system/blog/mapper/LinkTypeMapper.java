package com.angelblog.project.system.blog.mapper;

import com.angelblog.project.system.blog.domain.LinkType;

import java.util.List;

/**
 * 链接分类Mapper接口
 * 
 * @author alcedo
 * @date 2020-11-26
 */
public interface LinkTypeMapper 
{
    /**
     * 查询链接分类
     * 
     * @param id 链接分类ID
     * @return 链接分类
     */
    public LinkType selectLinkTypeById(Long id);

    /**
     * 根据分类代码查询链接分类
     *
     * @param type 链接分类代码
     * @return 链接分类
     */
    public LinkType selectLinkTypeByType(String type);

    /**
     * 查询链接分类列表
     * 
     * @param linkType 链接分类
     * @return 链接分类集合
     */
    public List<LinkType> selectLinkTypeList(LinkType linkType);

    /**
     * 新增链接分类
     * 
     * @param linkType 链接分类
     * @return 结果
     */
    public int insertLinkType(LinkType linkType);

    /**
     * 修改链接分类
     * 
     * @param linkType 链接分类
     * @return 结果
     */
    public int updateLinkType(LinkType linkType);

    /**
     * 删除链接分类
     * 
     * @param id 链接分类ID
     * @return 结果
     */
    public int deleteLinkTypeById(Long id);

    /**
     * 批量删除链接分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLinkTypeByIds(String[] ids);
}
