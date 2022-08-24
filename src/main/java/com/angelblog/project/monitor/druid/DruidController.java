package com.angelblog.project.monitor.druid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.angelblog.framework.web.controller.BaseController;

/**
 * druid 监控
 * 
 * @author Alcedo
 */
@Controller
@RequestMapping("/admin/monitor/data")
public class DruidController extends BaseController
{
    private String prefix = "/admin/monitor/druid";

    @RequiresPermissions("monitor:data:view")
    @GetMapping()
    public String index()
    {
        return redirect(prefix + "/index");
    }
}
