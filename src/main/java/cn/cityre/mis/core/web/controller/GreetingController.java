package cn.cityre.mis.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 刘大磊 on 2017/8/21 15:53.
 */
@Controller
public class GreetingController {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

}
