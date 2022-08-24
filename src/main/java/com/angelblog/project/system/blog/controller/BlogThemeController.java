package com.angelblog.project.system.blog.controller;

import com.angelblog.project.system.blog.domain.BlogTheme;
import com.angelblog.project.system.blog.service.IBlogThemeService;
import com.angelblog.common.annotation.Log;
import com.angelblog.common.core.controller.BaseController;
import com.angelblog.common.core.domain.AjaxResult;
import com.angelblog.common.core.page.TableDataInfo;
import com.angelblog.common.enums.BusinessType;
import com.angelblog.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客主题Controller
 * 
 * @author alcedo
 * @date 2020-12-20
 */
@Controller
@RequestMapping("/blog/blogTheme")
public class BlogThemeController extends BaseController
{
    private String prefix = "blog/blogTheme";

    @Autowired
    private IBlogThemeService blogThemeService;

    @RequiresPermissions("cms:blogTheme:view")
    @GetMapping()
    public String blogTheme()
    {
        return prefix + "/blogTheme";
    }

    /**
     * 查询博客主题列表
     */
    @RequiresPermissions("cms:blogTheme:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BlogTheme blogTheme)
    {
        startPage();
        List<BlogTheme> list = blogThemeService.selectBlogThemeList(blogTheme);
        return getDataTable(list);
    }

    /**
     * 导出博客主题列表
     */
    @RequiresPermissions("cms:blogTheme:export")
    @Log(title = "博客主题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BlogTheme blogTheme)
    {
        List<BlogTheme> list = blogThemeService.selectBlogThemeList(blogTheme);
        ExcelUtil<BlogTheme> util = new ExcelUtil<BlogTheme>(BlogTheme.class);
        return util.exportExcel(list, "theme");
    }

    /**
     * 新增博客主题
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存博客主题
     */
    @RequiresPermissions("cms:blogTheme:add")
    @Log(title = "博客主题", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BlogTheme blogTheme)
    {
        return toAjax(blogThemeService.insertBlogTheme(blogTheme));
    }

    /**
     * 修改博客主题
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BlogTheme blogTheme = blogThemeService.selectBlogThemeById(id);
        mmap.put("blogTheme", blogTheme);
        return prefix + "/edit";
    }

    /**
     * 修改保存博客主题
     */
    @RequiresPermissions("cms:blogTheme:edit")
    @Log(title = "博客主题", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BlogTheme blogTheme)
    {
        return toAjax(blogThemeService.updateBlogTheme(blogTheme));
    }

    /**
     * 删除博客主题
     */
    @RequiresPermissions("cms:blogTheme:remove")
    @Log(title = "博客主题", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(blogThemeService.deleteBlogThemeByIds(ids));
    }

    @PostMapping( "/updateBlogThemeConfig")
    @ResponseBody
    public AjaxResult updateBlogThemeConfig(String code){
        blogThemeService.updateBlogThemeConfig(code);
        return success();
    }
}
