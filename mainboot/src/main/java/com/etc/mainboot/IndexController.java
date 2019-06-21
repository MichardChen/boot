package com.etc.mainboot;

import com.etc.mainboot.annotation.OperateLog;
import com.etc.mainboot.enums.BusinessType;
import com.etc.mainboot.enums.OperatorType;
import com.etc.mainboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    UserService userService;

    @OperateLog(title = "hello",bizType = BusinessType.QUERY,operateType = OperatorType.CUSTOMER)
    @RequestMapping("/hello")
    public String hello(){
        userService.hello();
        return "hello world";
    }
}
