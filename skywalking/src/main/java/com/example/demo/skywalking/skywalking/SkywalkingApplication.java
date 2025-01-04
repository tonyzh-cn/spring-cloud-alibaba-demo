package com.example.demo.skywalking.skywalking;
/**
 * @author zhangtao
 * @since 2024/5/7 19:17
 *
 * -javaagent:E:\download\apache-skywalking-java-agent-8.11.0\skywalking-agent\skywalking-agent.jar
 * -DSW_AGENT_NAME=demo
 * -DSW_AGENT_COLLECTOR_BACKEND_SERVICES=39.101.204.19:11800
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SkywalkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingApplication.class, args);
    }

}
