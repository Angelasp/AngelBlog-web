package com.angelblog.project.system.blog.mapper;

import com.angelblog.project.system.blog.domain.Comment;
import java.util.List;

/**
 * 评论Mapper接口
 * 
 * @author alcedo
 * @date 2020-11-19
 */
public interface CommentMapper 
{
    /**
     * 查询评论
     * 
     * @param id 评论ID
     * @return 评论
     */
    public Comment selectCommentById(Long id);

    /**
     * 查询评论列表
     * 
     * @param comment 评论
     * @return 评论集合
     */
    public List<Comment> selectCommentList(Comment comment);


    /**
     * 用于前台查询评论列表
     *
     * @param comment 评论
     * @return 评论集合
     */
    public List<Comment> selectComments(Comment comment);
    /**
     * 新增评论
     * 
     * @param comment 评论
     * @return 结果
     */
    public int insertComment(Comment comment);

    /**
     * 修改评论
     * 
     * @param comment 评论
     * @return 结果
     */
    public int updateComment(Comment comment);

    /**
     * 删除评论
     * 
     * @param id 评论ID
     * @return 结果
     */
    public int deleteCommentById(Long id);

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCommentByIds(String[] ids);


    /**
     * 评论点赞+1
     * @param id
     * @return
     */
    public int upVote(String id);
}
