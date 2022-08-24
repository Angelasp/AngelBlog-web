package com.angelblog.project.monitor.operlog.controller;

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
import com.angelblog.common.utils.poi.ExcelUtil;
import com.angelblog.framework.aspectj.lang.annotation.Log;
import com.angelblog.framework.aspectj.lang.constant.BusinessType;
import com.angelblog.framework.web.controller.BaseController;
import com.angelblog.common.core.domain.AjaxResult;
import com.angelblog.framework.web.page.TableDataInfo;
import com.angelblog.project.monitor.operlog.domain.OperLog;
import com.angelblog.project.monitor.operlog.service.IOperLogService;

/**
 * 操作日志记录
 * 
 * @author Alcedo
 */
@Controller
@RequestMapping("/admin/monitor/operlog")
public class OperlogController extends BaseController
{
    private String prefix = "admin/monitor/operlog";

    @Autowired
    private IOperLogService operLogService;

    @RequiresPermissions("monitor:operlog:view")
    @GetMapping()
    public String operlog()
    {
        return prefix + "/operlog";
    }

    @RequiresPermissions("monitor:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OperLog operLog)
    {
        startPage();
        List<OperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志", action = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OperLog operLog) throws Exception
    {
        try
        {
            List<OperLog> list = operLogService.selectOperLogList(operLog);
            ExcelUtil<OperLog> util = new ExcelUtil<OperLog>(OperLog.class);
            return util.exportExcel(list, "operLog");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(operLogService.deleteOperLogByIds(ids));
    }

    @RequiresPermissions("monitor:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") Long deptId, ModelMap mmap)
    {
        mmap.put("operLog", operLogService.selectOperLogById(deptId));
        return prefix + "/detail";
    }
}
