package com.etc.mainboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ChenDang
 * @date 2019/6/21 0021
 */
@Controller
@RequestMapping("/web")
public class WebController {

    @RequestMapping("/helloshow")
    public String hello(){
        return "show";
    }
}
