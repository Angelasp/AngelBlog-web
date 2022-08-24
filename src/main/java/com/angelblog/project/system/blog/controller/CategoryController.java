package com.angelblog.project.system.blog.controller;

import com.angelblog.project.system.blog.domain.Category;
import com.angelblog.project.system.blog.service.ICategoryService;
import com.angelblog.common.annotation.Log;
import com.angelblog.common.core.controller.BaseController;
import com.angelblog.common.core.domain.AjaxResult;
import com.angelblog.common.core.domain.Ztree;
import com.angelblog.common.enums.BusinessType;
import com.angelblog.common.utils.StringUtils;
import com.angelblog.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 栏目分类Controller
 * 
 * @author alcedo
 * @date 2020-10-28
 */
@Controller
@RequestMapping("/blog/category")
public class CategoryController extends BaseController
{
    private String prefix = "blog/category";

    @Autowired
    private ICategoryService categoryService;

    @RequiresPermissions("blog:category:view")
    @GetMapping()
    public String category()
    {
        return prefix + "/category";
    }

    /**
     * 查询栏目分类树列表
     */
    @RequiresPermissions("blog:category:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Category> list(Category category)
    {
        List<Category> list = categoryService.selectCategoryList(category);
        return list;
    }

    /**
     * 导出栏目分类列表
     */
    @RequiresPermissions("blog:category:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Category category)
    {
        List<Category> list = categoryService.selectCategoryList(category);
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 新增栏目分类
     */
    @GetMapping(value = { "/add/{categoryId}", "/add/" })
    public String add(@PathVariable(value = "categoryId", required = false) Long categoryId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(categoryId))
        {
            mmap.put("category", categoryService.selectCategoryById(categoryId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存栏目分类
     */
    @RequiresPermissions("blog:category:add")
    @Log(title = "栏目分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Category category)
    {
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改栏目分类
     */
    @GetMapping("/edit/{categoryId}")
    public String edit(@PathVariable("categoryId") Long categoryId, ModelMap mmap)
    {
        Category category = categoryService.selectCategoryById(categoryId);
        mmap.put("category", category);
        return prefix + "/edit";
    }

    /**
     * 修改保存栏目分类
     */
    @RequiresPermissions("blog:category:edit")
    @Log(title = "栏目分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Category category)
    {
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除
     */
    @RequiresPermissions("blog:category:remove")
    @Log(title = "栏目分类", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{categoryId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("categoryId") String categoryId)
    {
        return toAjax(categoryService.deleteCategoryById(categoryId));
    }

    /**
     * 选择栏目分类树
     */
    @GetMapping(value = { "/selectCategoryTree/{categoryId}", "/selectCategoryTree/" })
    public String selectCategoryTree(@PathVariable(value = "categoryId", required = false) Long categoryId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(categoryId))
        {
            mmap.put("category", categoryService.selectCategoryById(categoryId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载栏目分类树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = categoryService.selectCategoryTree();
        return ztrees;
    }
}
