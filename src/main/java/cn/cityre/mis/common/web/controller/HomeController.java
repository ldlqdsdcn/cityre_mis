package cn.cityre.mis.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 刘大磊 on 2017/8/22 11:25.
 * 首页
 */
@Controller
public class HomeController {
    @RequestMapping({"/home", "/"})
    public ModelAndView showHome() {
        return new ModelAndView("home");
    }
}
