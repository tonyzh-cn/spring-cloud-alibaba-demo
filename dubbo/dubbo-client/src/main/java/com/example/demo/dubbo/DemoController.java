package com.example.demo.dubbo;

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
    @Resource
    private DubboClient dubboClient;


    @RequestMapping("/dubbo")
    @ResponseBody
    public String dubbo() {
        dubboClient.echo();
        return "调用dubbo接口成功";
    }

    @RequestMapping("/error")
    @ResponseBody
    public String error(){
        throw new RuntimeException();
    }
}
