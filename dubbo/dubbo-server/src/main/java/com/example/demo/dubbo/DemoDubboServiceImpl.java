package com.example.demo.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author zhangtao
 * @since 2024/5/20 16:39
 */
@DubboService(version = "1.0.0", group = "dubbo-server-demo")
public class DemoDubboServiceImpl implements DemoDubboService{
    public void echo(){
        System.out.println("hello");
    }
}
