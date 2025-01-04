package com.example.demo.skywalking.skywalking;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.CommonResult;
import com.example.demo.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author zhangtao
 * @since 2024/5/7 19:17
 *
 * -javaagent:E:\download\apache-skywalking-java-agent-8.11.0\skywalking-agent\skywalking-agent.jar
 * -DSW_AGENT_NAME=demo
 * -DSW_AGENT_COLLECTOR_BACKEND_SERVICES=39.101.204.19:11800
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @Resource
    private DemoFeignApi demoFeignApi;

    @RequestMapping("/echo")
    @ResponseBody
    public CommonResult echo() {
        CommonResult result = demoFeignApi.feign();

        User user = JSON.parseObject(result.getData().toString(), User.class);
        return result;
    }

    @RequestMapping("/error")
    @ResponseBody
    public String error(){
        throw new RuntimeException();
    }
}
