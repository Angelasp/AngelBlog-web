package com.angelblog.project.monitor.job.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author Alcedo
 */
@Component("angelTask")
public class AngelTask
{

    public void angelParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void angelNoParams()
    {
        System.out.println("执行无参方法");
    }

}
