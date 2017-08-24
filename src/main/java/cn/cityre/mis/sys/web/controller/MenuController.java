package cn.cityre.mis.sys.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 刘大磊 on 2017/8/24 17:29.
 */
@Controller
public class MenuController {
    @RequiresPermissions("sys_menu")
    @RequestMapping("/sys/menuList")
    public ModelAndView showList()
    {
        ModelAndView modelAndView=new ModelAndView("sys/menuList");
        return modelAndView;
    }
}
