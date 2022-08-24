package com.angelblog.project.system.blog.controller;

import com.angelblog.project.system.blog.domain.Resource;
import com.angelblog.project.system.blog.service.IResourceService;
import com.angelblog.common.annotation.Log;
import com.angelblog.common.config.Global;
import com.angelblog.common.core.controller.BaseController;
import com.angelblog.common.core.domain.AjaxResult;
import com.angelblog.common.core.page.TableDataInfo;
import com.angelblog.common.enums.BusinessType;
import com.angelblog.common.utils.file.FileUploadUtils;
import com.angelblog.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源Controller
 * 
 * @author alcedo
 * @date 2020-11-23
 */
@Controller
@RequestMapping("/blog/resource")
public class ResourceController extends BaseController
{
    private String prefix = "blog/resource";

    @Autowired
    private IResourceService resourceService;

    @RequiresPermissions("blog:resource:view")
    @GetMapping()
    public String resource()
    {
        return prefix + "/resource";
    }

    /**
     * 查询资源列表
     */
    @RequiresPermissions("blog:resource:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Resource resource)
    {
        startPage();
        List<Resource> list = resourceService.selectResourceList(resource);
        return getDataTable(list);
    }

    /**
     * 导出资源列表
     */
    @RequiresPermissions("blog:resource:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Resource resource)
    {
        List<Resource> list = resourceService.selectResourceList(resource);
        ExcelUtil<Resource> util = new ExcelUtil<Resource>(Resource.class);
        return util.exportExcel(list, "resource");
    }

    /**
     * 新增资源
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存资源
     */
    @RequiresPermissions("blog:resource:add")
    @Log(title = "资源", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Resource resource)
    {
        return toAjax(resourceService.insertResource(resource));
    }

    /**
     * 修改资源
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Resource resource = resourceService.selectResourceById(id);
        mmap.put("resource", resource);
        return prefix + "/edit";
    }

    /**
     * 修改保存资源
     */
    @RequiresPermissions("blog:resource:edit")
    @Log(title = "资源", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Resource resource)
    {
        return toAjax(resourceService.updateResource(resource));
    }

    /**
     * 删除资源
     */
    @RequiresPermissions("blog:resource:remove")
    @Log(title = "资源", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(resourceService.deleteResourceByIds(ids));
    }


    /**
     * 上传资源请求
     */
    @PostMapping("/uploadResource")
    @ResponseBody
    public AjaxResult uploadResource(MultipartFile file) throws Exception
    {
        try
        {
            // 上传并返回新文件名称
            String path = FileUploadUtils.upload(Global.getResourcePath(), file);
            Map map=new HashMap();
            map.put("path",path);
            map.put("size",file.getSize());
            map.put("name",file.getOriginalFilename());
            return AjaxResult.success(map);
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
