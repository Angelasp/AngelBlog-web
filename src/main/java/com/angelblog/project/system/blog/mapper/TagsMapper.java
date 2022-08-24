package com.angelblog.project.system.blog.mapper;

import com.angelblog.project.system.blog.domain.Tags;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签管理Mapper接口
 * 
 * @author alcedo
 * @date 2021-11-28
 */
public interface TagsMapper 
{
    /**
     * 查询标签管理
     * 
     * @param tagId 标签管理ID
     * @return 标签管理
     */
    public Tags selectTagsById(Long tagId);


    public Tags getTagByName(@Param("type") String type,@Param("name")  String name);

    /**
     * 查询标签管理列表
     * 
     * @param tags 标签管理
     * @return 标签管理集合
     */
    public List<Tags> selectTagsList(Tags tags);

    /**
     * 新增标签管理
     * 
     * @param tags 标签管理
     * @return 结果
     */
    public int insertTags(Tags tags);

    /**
     * 修改标签管理
     * 
     * @param tags 标签管理
     * @return 结果
     */
    public int updateTags(Tags tags);

    /**
     * 删除标签管理
     * 
     * @param tagId 标签管理ID
     * @return 结果
     */
    public int deleteTagsById(Long tagId);

    /**
     * 批量删除标签管理
     * 
     * @param tagIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTagsByIds(String[] tagIds);

    /**
     * 查询所有标签
     * @return
     */
    public List<Tags> selectTagsAll();

    /**
     * 查询前台页面fullTabs标签
     * @return
     */
    public  List<Tags> selectBlogTabs();
}
