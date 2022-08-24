package com.angelblog.project.system.blog.domain;

import com.angelblog.common.annotation.Excel;
import com.angelblog.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 素材分组对象 cms_material_group
 * 
 * @author alcedo
 * @date 2020-11-07
 */
public class MaterialGroup extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long groupId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private String deptId;

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String groupName;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 创建人用户ID */
    private String userId;

    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setDeptId(String deptId) 
    {
        this.deptId = deptId;
    }

    public String getDeptId() 
    {
        return deptId;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("parentId", getParentId())
            .append("deptId", getDeptId())
            .append("groupName", getGroupName())
            .append("description", getDescription())
            .append("sort", getSort())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
