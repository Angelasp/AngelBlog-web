package com.angelblog.project.system.blog.service.impl;

import com.angelblog.project.system.blog.domain.Category;
import com.angelblog.project.system.blog.mapper.CategoryMapper;
import com.angelblog.project.system.blog.service.ICategoryService;
import com.angelblog.common.core.domain.Ztree;
import com.angelblog.common.core.text.Convert;
import com.angelblog.common.utils.DateUtils;
import com.angelblog.common.utils.security.ShiroUtils;
import com.angelblog.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 栏目分类Service业务层处理
 * 
 * @author alcedo
 * @date 2020-10-28
 */
@Service
public class CategoryServiceImpl implements ICategoryService 
{
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询栏目分类
     * 
     * @param categoryId 栏目分类ID
     * @return 栏目分类
     */
    @Override
    public Category selectCategoryById(Long categoryId)
    {
        return categoryMapper.selectCategoryById(categoryId);
    }

    /**
     * 查询栏目分类列表
     * 
     * @param category 栏目分类
     * @return 栏目分类
     */
    @Override
    public List<Category> selectCategoryList(Category category)
    {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增栏目分类
     * 
     * @param category 栏目分类
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCategory(Category category)
    {
        category.setCreateTime(DateUtils.getNowDate());
        User user=ShiroUtils.getUser();
        category.setCreateBy(user.getUserId().toString());
        int n=categoryMapper.insertCategory(category);
        //更新parentids
        Long pid=category.getParentId();
        Category p=categoryMapper.selectCategoryById(pid);
        String ancestors="";
        if(p!=null){
            ancestors = p.getAncestors();
            ancestors += category.getCategoryId()+",";
        }else{
            ancestors=category.getCategoryId()+",";
        }
        category.setAncestors(ancestors);
        n=categoryMapper.updateCategory(category);
        return n;
    }

    /**
     * 修改栏目分类
     * 
     * @param category 栏目分类
     * @return 结果
     */
    @Override
    public int updateCategory(Category category)
    {
        category.setUpdateTime(DateUtils.getNowDate());
        User user=ShiroUtils.getUser();
        category.setUpdateBy(user.getUserId().toString());
        return categoryMapper.updateCategory(category);
    }

    /**
     * 删除栏目分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(String ids)
    {
        return categoryMapper.deleteCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除栏目分类信息
     * 
     * @param categoryId 栏目分类ID
     * @return 结果
     */
    @Override
    public int deleteCategoryById(String categoryId)
    {
        return categoryMapper.deleteCategoryById(categoryId);
    }

    /**
     * 查询栏目分类树列表
     * 
     * @return 所有栏目分类信息
     */
    @Override
    public List<Ztree> selectCategoryTree()
    {
        List<Category> categoryList = categoryMapper.selectCategoryList(new Category());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (Category category : categoryList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(category.getCategoryId());
            ztree.setpId(category.getParentId());
            ztree.setName(category.getCategoryName());
            ztree.setTitle(category.getCategoryName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    @Override
    public List<Category> selectNavCategories(Category category) {
        return categoryMapper.selectNavCategories(category);
    }
}
