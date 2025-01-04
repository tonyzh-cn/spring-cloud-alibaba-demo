package com.example.demo.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.demo.api.CommonResult;
import com.example.demo.api.User;
import com.example.demo.sentinel.aop.Idempotent;
import com.example.demo.sentinel.openfeign.DemoFeignApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author zhangtao
 * @since 2024/5/7 19:17
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    private final static String RESOURCE_NAME="resource";
    @Resource
    private DemoFeignApi demoFeignApi;

    @RequestMapping("/echo")
    @ResponseBody
    public String echo() throws InterruptedException {
        Thread.sleep(1000);
        return "hello";
    }

    @RequestMapping("/echo2")
    @ResponseBody
    @SentinelResource(value = RESOURCE_NAME, blockHandler = "exceptionHandler", fallback = "helloFallback")
    public String echo2() throws InterruptedException {
        Thread.sleep(1000);
        return "hello";
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at ";
    }

    @RequestMapping("/echo3")
    @ResponseBody
    public String echo3() {
        return demoFeignApi.feign();
    }

    @RequestMapping("/feign")
    @ResponseBody
    @Idempotent
    public CommonResult feign() {
        User user=new User();
        user.setId(2L);
        user.setName("lisi");
        return CommonResult.success(user);
    }

    @RequestMapping("/error")
    @ResponseBody
    public String error(){
        throw new RuntimeException();
    }
}
