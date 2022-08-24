package com.angelblog.project.system.blog.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.angelblog.common.annotation.Log;
import com.angelblog.common.enums.BusinessType;
import com.angelblog.project.system.blog.domain.FriendLink;
import com.angelblog.project.system.blog.service.IFriendLinkService;
import com.angelblog.common.core.controller.BaseController;
import com.angelblog.common.core.domain.AjaxResult;
import com.angelblog.common.utils.poi.ExcelUtil;
import com.angelblog.common.core.page.TableDataInfo;

/**
 * 友情链接Controller
 *
 * @author alcedo
 * @date 2020-11-16
 */
@Controller
@RequestMapping("/blog/friendLink")
public class FriendLinkController extends BaseController
{
    private String prefix = "blog/friendLink";

    @Autowired
    private IFriendLinkService friendLinkService;

    @RequiresPermissions("blog:friendLink:view")
    @GetMapping()
    public String friendLink()
    {
        return prefix + "/friendLink";
    }

    /**
     * 查询友情链接列表
     */
    @RequiresPermissions("blog:friendLink:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FriendLink friendLink)
    {
        startPage();
        List<FriendLink> list = friendLinkService.selectFriendLinkList(friendLink);
        return getDataTable(list);
    }

    /**
     * 导出友情链接列表
     */
    @RequiresPermissions("blog:friendLink:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FriendLink friendLink)
    {
        List<FriendLink> list = friendLinkService.selectFriendLinkList(friendLink);
        ExcelUtil<FriendLink> util = new ExcelUtil<FriendLink>(FriendLink.class);
        return util.exportExcel(list, "friendLink");
    }

    /**
     * 新增友情链接
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存友情链接
     */
    @RequiresPermissions("blog:friendLink:add")
    @Log(title = "友情链接", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FriendLink friendLink)
    {
        return toAjax(friendLinkService.insertFriendLink(friendLink));
    }

    /**
     * 修改友情链接
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        FriendLink friendLink = friendLinkService.selectFriendLinkById(id);
        mmap.put("friendLink", friendLink);
        return prefix + "/edit";
    }

    /**
     * 修改保存友情链接
     */
    @RequiresPermissions("blog:friendLink:edit")
    @Log(title = "友情链接", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FriendLink friendLink)
    {
        return toAjax(friendLinkService.updateFriendLink(friendLink));
    }

    /**
     * 删除友情链接
     */
    @RequiresPermissions("blog:friendLink:remove")
    @Log(title = "友情链接", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(friendLinkService.deleteFriendLinkByIds(ids));
    }
}
