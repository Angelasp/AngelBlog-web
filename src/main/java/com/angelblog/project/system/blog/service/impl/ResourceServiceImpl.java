package com.angelblog.project.system.blog.service.impl;

import java.util.List;
import com.angelblog.common.utils.DateUtils;
import com.angelblog.common.utils.Guid;
import com.angelblog.common.utils.security.ShiroUtils;
import com.angelblog.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.angelblog.project.system.blog.mapper.ResourceMapper;
import com.angelblog.project.system.blog.domain.Resource;
import com.angelblog.project.system.blog.service.IResourceService;
import com.angelblog.common.core.text.Convert;

/**
 * 资源Service业务层处理
 * 
 * @author alcedo
 * @date 2020-11-23
 */
@Service
public class ResourceServiceImpl implements IResourceService 
{
    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 查询资源
     * 
     * @param id 资源ID
     * @return 资源
     */
    @Override
    public Resource selectResourceById(String id)
    {
        return resourceMapper.selectResourceById(id);
    }

    /**
     * 查询资源列表
     * 
     * @param resource 资源
     * @return 资源
     */
    @Override
    public List<Resource> selectResourceList(Resource resource)
    {
        return resourceMapper.selectResourceList(resource);
    }

    /**
     * 新增资源
     * 
     * @param resource 资源
     * @return 结果
     */
    @Override
    public int insertResource(Resource resource)
    {
        resource.setId(Guid.get());
        User user= ShiroUtils.getUser();
        resource.setUserId(user.getUserId().toString());
        resource.setUserName(user.getUserName());

        resource.setCreateTime(DateUtils.getNowDate());
        return resourceMapper.insertResource(resource);
    }

    /**
     * 修改资源
     * 
     * @param resource 资源
     * @return 结果
     */
    @Override
    public int updateResource(Resource resource)
    {
        resource.setUpdateTime(DateUtils.getNowDate());
        return resourceMapper.updateResource(resource);
    }

    /**
     * 删除资源对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteResourceByIds(String ids)
    {
        return resourceMapper.deleteResourceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资源信息
     * 
     * @param id 资源ID
     * @return 结果
     */
    @Override
    public int deleteResourceById(String id)
    {
        return resourceMapper.deleteResourceById(id);
    }

    @Override
    public int upVote(String id) {
        return resourceMapper.upVote(id);
    }

    @Override
    public int resourceLook(String id) {
        return resourceMapper.resourceLook(id);
    }
}
