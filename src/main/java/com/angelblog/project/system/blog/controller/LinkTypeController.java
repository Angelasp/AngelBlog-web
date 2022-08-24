package com.angelblog.project.system.blog.controller;

import com.angelblog.project.system.blog.domain.LinkType;
import com.angelblog.project.system.blog.service.ILinkTypeService;
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
 * 链接分类Controller
 * 
 * @author alcedo
 * @date 2020-11-26
 */
@Controller
@RequestMapping("/blog/linkType")
public class LinkTypeController extends BaseController
{
    private String prefix = "blog/linkType";

    @Autowired
    private ILinkTypeService linkTypeService;

    @RequiresPermissions("cms:linkType:view")
    @GetMapping()
    public String linkType()
    {
        return prefix + "/linkType";
    }

    /**
     * 查询链接分类列表
     */
    @RequiresPermissions("cms:linkType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(LinkType linkType)
    {
        startPage();
        List<LinkType> list = linkTypeService.selectLinkTypeList(linkType);
        return getDataTable(list);
    }

    /**
     * 导出链接分类列表
     */
    @RequiresPermissions("cms:linkType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(LinkType linkType)
    {
        List<LinkType> list = linkTypeService.selectLinkTypeList(linkType);
        ExcelUtil<LinkType> util = new ExcelUtil<LinkType>(LinkType.class);
        return util.exportExcel(list, "linkType");
    }

    /**
     * 新增链接分类
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存链接分类
     */
    @RequiresPermissions("cms:linkType:add")
    @Log(title = "链接分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(LinkType linkType)
    {
        return toAjax(linkTypeService.insertLinkType(linkType));
    }

    /**
     * 修改链接分类
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        LinkType linkType = linkTypeService.selectLinkTypeById(id);
        mmap.put("linkType", linkType);
        return prefix + "/edit";
    }

    /**
     * 修改保存链接分类
     */
    @RequiresPermissions("cms:linkType:edit")
    @Log(title = "链接分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(LinkType linkType)
    {
        return toAjax(linkTypeService.updateLinkType(linkType));
    }

    /**
     * 删除链接分类
     */
    @RequiresPermissions("cms:linkType:remove")
    @Log(title = "链接分类", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(linkTypeService.deleteLinkTypeByIds(ids));
    }
}
