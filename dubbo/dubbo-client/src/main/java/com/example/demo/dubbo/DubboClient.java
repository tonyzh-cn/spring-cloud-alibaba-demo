package com.example.demo.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author zhangtao
 * @since 2024/5/20 16:44
 */
@Service
public class DubboClient {
    @DubboReference(version = "1.0.0", group = "dubbo-server-demo")
    private DemoDubboService demoDubboService;

    public void echo(){
        demoDubboService.echo();
    }
}
