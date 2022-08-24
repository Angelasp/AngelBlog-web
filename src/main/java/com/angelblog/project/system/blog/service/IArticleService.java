package com.angelblog.project.system.blog.service;

import com.angelblog.project.system.blog.domain.Article;
import com.angelblog.project.system.blog.domain.ArticleRegionType;

import java.util.List;

/**
 * 文章管理Service接口
 * 
 * @author alcedo
 * @date 2020-10-28
 */
public interface IArticleService 
{
    /**
     * 查询文章管理
     * 
     * @param id 文章管理ID
     * @return 文章管理
     */
    public Article selectArticleById(String id);

    /**
     * 查询文章管理列表
     * 
     * @param article 文章管理
     * @return 文章管理集合
     */
    public List<Article> selectArticleList(Article article);

    /**
     * 新增文章管理
     * 
     * @param article 文章管理
     * @return 结果
     */
    public int insertArticle(Article article);

    /**
     * 修改文章管理
     * 
     * @param article 文章管理
     * @return 结果
     */
    public int updateArticle(Article article);

    /**
     * 批量删除文章管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteArticleByIds(String ids);

    /**
     * 删除文章管理信息
     * 
     * @param id 文章管理ID
     * @return 结果
     */
    public int deleteArticleById(String id);

    /**
     * 根据文章专区查询文章列表
     * @param articleRegionType
     * @return
     */
    public List<Article> selectArticlesByArticleRegionType(ArticleRegionType articleRegionType);


    /**
     * 查询文章专区不为空的文章列表，这类文章才可以在博客首页展示
     *
     * @param article 文章
     * @return 文章集合
     */
    public List<Article> selectArticlesRegionNotNull(Article article);

    /**
     * 查询文章专区为空的文章，这类文章可用于猜你喜欢来推荐
     * 注意这类文章和上面的区别是：
     * 上面方法的文章是网站管理员精心编辑策划的、来画好专区的，是由很大几率展示的
     * 而下面的文章属于坐冷板凳的
     *
     * @param article 文章
     * @return 文章集合
     */
    public List<Article> selectArticlesRegionIsNull(Article article);

    /**
     * 文章点赞+1
     * @param id
     * @return
     */
    public int upVote(String id);
    /**
     * 文章点击数+1
     * @param id
     * @return
     */
    public int articleLook(String id);

}
