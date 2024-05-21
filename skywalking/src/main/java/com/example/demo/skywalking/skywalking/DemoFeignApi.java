package com.example.demo.skywalking.skywalking;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangtao
 * @since 2024/5/15 21:57
 */
@FeignClient(name = "sentinel-demo",path = "/demo")
public interface DemoFeignApi {
    @RequestMapping(value = "/feign", method = RequestMethod.POST)
    @ResponseBody
    public String feign();
}
