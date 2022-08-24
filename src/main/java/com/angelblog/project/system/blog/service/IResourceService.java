package com.angelblog.project.system.blog.service;

import com.angelblog.project.system.blog.domain.Resource;
import java.util.List;

/**
 * 资源Service接口
 * 
 * @author alcedo
 * @date 2020-11-23
 */
public interface IResourceService 
{
    /**
     * 查询资源
     * 
     * @param id 资源ID
     * @return 资源
     */
    public Resource selectResourceById(String id);

    /**
     * 查询资源列表
     * 
     * @param resource 资源
     * @return 资源集合
     */
    public List<Resource> selectResourceList(Resource resource);

    /**
     * 新增资源
     * 
     * @param resource 资源
     * @return 结果
     */
    public int insertResource(Resource resource);

    /**
     * 修改资源
     * 
     * @param resource 资源
     * @return 结果
     */
    public int updateResource(Resource resource);

    /**
     * 批量删除资源
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteResourceByIds(String ids);

    /**
     * 删除资源信息
     * 
     * @param id 资源ID
     * @return 结果
     */
    public int deleteResourceById(String id);

    /**
     * 点赞+1
     * @param id
     * @return
     */
    public int upVote(String id);
    /**
     * 点击数+1
     * @param id
     * @return
     */
    public int resourceLook(String id);
}
