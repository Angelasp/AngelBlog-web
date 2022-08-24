package com.angelblog.project.system.user.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.angelblog.framework.config.AngelBlogConfig;
import com.angelblog.framework.web.controller.BaseController;
import com.angelblog.project.system.menu.domain.Menu;
import com.angelblog.project.system.menu.service.IMenuService;
import com.angelblog.project.system.user.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页 业务处理
 * 
 * @author Alcedo
 */
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;

    @Autowired
    private AngelBlogConfig angelblogConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        User user = getUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUserId(user.getUserId());
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", angelblogConfig.getCopyrightYear());
        return "admin/index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", angelblogConfig.getVersion());
        return "admin/main";
    }

}
