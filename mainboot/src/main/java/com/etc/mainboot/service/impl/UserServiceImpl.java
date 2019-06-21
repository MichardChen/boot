package com.etc.mainboot.service.impl;

import com.etc.mainboot.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author ChenDang
 * @date 2019/5/28 0028
 */
@Service
public class UserServiceImpl implements UserService {

    @Async("asyncServiceExecutor")
    @Override
    public void hello(){
        System.out.println("当前运行的线程名称：" + Thread.currentThread().getName());
    }
}
