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

    @RequestMapping({"index", "/"})
    public ModelAndView showHome() {
        return new ModelAndView("index");
    }
    @RequestMapping("kouhao")
    public String showKouhao()
    {
        return "common/kouhao";
    }
}
