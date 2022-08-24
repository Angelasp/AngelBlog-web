package com.angelblog.project.system.blog.service.impl;

import com.angelblog.project.system.blog.domain.BlogTheme;
import com.angelblog.project.system.blog.mapper.BlogThemeMapper;
import com.angelblog.project.system.blog.service.IBlogThemeService;
import com.angelblog.common.core.text.Convert;
import com.angelblog .common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客主题Service业务层处理
 * 
 * @author alcedo
 * @date 2020-12-20
 */
@Service
public class BlogThemeServiceImpl implements IBlogThemeService 
{
    @Autowired
    private BlogThemeMapper blogThemeMapper;

    /**
     * 查询博客主题
     * 
     * @param id 博客主题ID
     * @return 博客主题
     */
    @Override
    public BlogTheme selectBlogThemeById(Long id)
    {
        return blogThemeMapper.selectBlogThemeById(id);
    }

    /**
     * 查询博客主题列表
     * 
     * @param blogTheme 博客主题
     * @return 博客主题
     */
    @Override
    public List<BlogTheme> selectBlogThemeList(BlogTheme blogTheme)
    {
        return blogThemeMapper.selectBlogThemeList(blogTheme);
    }

    /**
     * 新增博客主题
     * 
     * @param blogTheme 博客主题
     * @return 结果
     */
    @Override
    public int insertBlogTheme(BlogTheme blogTheme)
    {
        blogTheme.setCreateTime(DateUtils.getNowDate());
        return blogThemeMapper.insertBlogTheme(blogTheme);
    }

    /**
     * 修改博客主题
     * 
     * @param blogTheme 博客主题
     * @return 结果
     */
    @Override
    public int updateBlogTheme(BlogTheme blogTheme)
    {
        return blogThemeMapper.updateBlogTheme(blogTheme);
    }

    /**
     * 删除博客主题对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBlogThemeByIds(String ids)
    {
        return blogThemeMapper.deleteBlogThemeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除博客主题信息
     * 
     * @param id 博客主题ID
     * @return 结果
     */
    @Override
    public int deleteBlogThemeById(Long id)
    {
        return blogThemeMapper.deleteBlogThemeById(id);
    }

    @Override
    public int updateBlogThemeConfig(String theme) {
        return blogThemeMapper.updateBlogThemeConfig(theme);
    }

    @Override
    public String queryCurrentBlogTheme() {
        return blogThemeMapper.queryCurrentBlogTheme();
    }
}
