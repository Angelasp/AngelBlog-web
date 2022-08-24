package com.angelblog.project.tool.build;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.angelblog.framework.web.controller.BaseController;

/**
 * build 表单构建
 * 
 * @author Alcedo
 */
@Controller
@RequestMapping("/admin/tool/build")
public class BuildController extends BaseController
{
    private String prefix = "admin/tool/build";

    @RequiresPermissions("tool:build:view")
    @GetMapping()
    public String build()
    {
        return prefix + "/build";
    }
}
