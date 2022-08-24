package com.angelblog.project.system.blog.controller;

import com.angelblog.project.system.blog.domain.Material;
import com.angelblog.project.system.blog.domain.MaterialUse;
import com.angelblog.project.system.blog.service.IMaterialService;
import com.angelblog.common.annotation.Log;
import com.angelblog.common.core.controller.BaseController;
import com.angelblog.common.core.domain.AjaxResult;
import com.angelblog.common.core.page.TableDataInfo;
import com.angelblog.common.enums.BusinessType;
import com.angelblog.common.utils.StringUtils;
import com.angelblog.common.utils.poi.ExcelUtil;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 素材Controller
 * 
 * @author alcedo
 * @date 2020-11-05
 */
@Controller
@RequestMapping("/blog/material")
public class MaterialController extends BaseController
{
    private String prefix = "blog/material";

    @Autowired
    private IMaterialService materialService;

    @RequiresPermissions("blog:material:view")
    @GetMapping()
    public String material()
    {
        return prefix + "/material";
    }

    /**
     * 查询素材列表
     */
    @RequiresPermissions("blog:material:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Material material)
    {
        startPage();
        List<Material> list = materialService.selectMaterialList(material);
        return getDataTable(list);
    }

    /**
     * 导出素材列表
     */
    @RequiresPermissions("blog:material:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Material material)
    {
        List<Material> list = materialService.selectMaterialList(material);
        ExcelUtil<Material> util = new ExcelUtil<Material>(Material.class);
        return util.exportExcel(list, "material");
    }

    /**
     * 新增素材
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        Material material=new Material();
        material.setGroupId("1");
        material.setGroupName("默认分组");
        mmap.put("material", material);
        return prefix + "/add";
    }

    /**
     * 新增保存素材
     */
    @RequiresPermissions("blog:material:add")
    @Log(title = "素材", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Material material)
    {
        return toAjax(materialService.insertMaterial(material));
    }

    /**
     * 修改素材
     */
    @GetMapping("/edit/{materialId}")
    public String edit(@PathVariable("materialId") String materialId, ModelMap mmap)
    {
        Material material = materialService.selectMaterialById(materialId);
        mmap.put("material", material);
        return prefix + "/edit";
    }

    /**
     * 修改保存素材
     */
    @RequiresPermissions("blog:material:edit")
    @Log(title = "素材", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Material material)
    {
        return toAjax(materialService.updateMaterial(material));
    }

    /**
     * 删除素材
     */
    @RequiresPermissions("blog:material:remove")
    @Log(title = "素材", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        int n=materialService.deleteMaterialByIds(ids);
        if(n==-1){
            return error("当前素材有正在使用中的,无法删除!");
        }
        return success();
    }


    /**
     * 删除素材
     */
    @RequiresPermissions("blog:material:audit")
    @Log(title = "素材审核", businessType = BusinessType.OTHER)
    @PostMapping( "/audit")
    @ResponseBody
    public AjaxResult audit(String ids)
    {
        return toAjax(materialService.auditMaterialByIds(ids));
    }



    /**
     * 跳转使用记录页面
     */
    @RequiresPermissions("blog:material:materialUse")
    @GetMapping("/toUseList/{materialId}")
    public String toUseList(@PathVariable("materialId")String materialId, ModelMap mmap)
    {
        mmap.put("materialId", materialId);
        return prefix + "/materialUse";
    }
    /**
     * 查询使用记录列表
     */
    @PostMapping("/selectMaterialUseList")
    @ResponseBody
    public TableDataInfo selectMaterialUseList(MaterialUse materialUse)
    {
        startPage();
        List<MaterialUse> list = materialService.selectMaterialUseList(materialUse);
        return getDataTable(list);
    }

    /**
     * 删除素材使用记录
     * @param materialUse
     * @return
     */
    @PostMapping( "/deleteMaterialUseBatch")
    @ResponseBody
    public AjaxResult deleteMaterialUseBatch(MaterialUse materialUse)
    {
        materialService.deleteMaterialUseBatch(materialUse);
        return success();
    }

    /**
     * 打开素材选择界面
     * @return
     */
    @GetMapping("/selectMaterialWithGroup")
    public String selectMaterialWithGroup(HttpServletRequest request, ModelMap mmap)
    {
        String materialId=request.getParameter("materialId");
        String materialName=request.getParameter("materialName");
        String materialPath=request.getParameter("materialPath");
        mmap.put("materialId", StringUtils.isNotEmpty(materialId)?materialId:"");
        mmap.put("materialName",StringUtils.isNotEmpty(materialName)?materialName:"");
        mmap.put("materialPath",StringUtils.isNotEmpty(materialPath)?materialPath:"");
        return prefix + "/selectMaterialWithGroup";
    }

}
