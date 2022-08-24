package com.angelblog.project.monitor.job.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.angelblog.common.utils.poi.ExcelUtil;
import com.angelblog.framework.aspectj.lang.annotation.Log;
import com.angelblog.framework.aspectj.lang.constant.BusinessType;
import com.angelblog.framework.web.controller.BaseController;
import com.angelblog.common.core.domain.AjaxResult;
import com.angelblog.framework.web.page.TableDataInfo;
import com.angelblog.project.monitor.job.domain.JobLog;
import com.angelblog.project.monitor.job.service.IJobLogService;

/**
 * 调度日志操作处理
 * 
 * @author Alcedo
 */
@Controller
@RequestMapping("/admin/monitor/jobLog")
public class JobLogController extends BaseController
{
    private String prefix = "admin/monitor/job";

    @Autowired
    private IJobLogService jobLogService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping()
    public String jobLog()
    {
        return prefix + "/jobLog";
    }

    @RequiresPermissions("monitor:job:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(JobLog jobLog)
    {
        startPage();
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        return getDataTable(list);
    }

    @Log(title = "调度日志", action = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(JobLog jobLog) throws Exception
    {
        try
        {
            List<JobLog> list = jobLogService.selectJobLogList(jobLog);
            ExcelUtil<JobLog> util = new ExcelUtil<JobLog>(JobLog.class);
            return util.exportExcel(list, "jobLog");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    @Log(title = "调度日志", action = BusinessType.DELETE)
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(jobLogService.deleteJobLogByIds(ids));
    }
}
