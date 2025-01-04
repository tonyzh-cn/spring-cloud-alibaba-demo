package com.example.demo.sentinel.degrade;

import com.example.demo.sentinel.openfeign.DemoFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhangtao
 * @since 2024/6/11 16:29
 */
@Component
public class DemoFeignFallbackFactory implements FallbackFactory<DemoFeignApi> {
    @Override
    public DemoFeignApi create(Throwable cause) {
        return new DemoFeignApi(){

            @Override
            public String feign() {
                return "降级了";
            }
        };
    }
}
