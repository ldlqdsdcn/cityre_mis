package cn.cityre.mis.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 刘大磊 on 2017/8/21 16:12.
 */
@Controller
public class SampleController {

    @RequestMapping("/hello")
    @ResponseBody
    String hello() {
        return "Hello World!";
    }
    @RequestMapping("/home")
    public ModelAndView home()
    {
        return new ModelAndView("home2");
    }

}