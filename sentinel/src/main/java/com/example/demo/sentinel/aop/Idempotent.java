package com.example.demo.sentinel.aop;

import java.lang.annotation.*;

/**
 * @author zhangtao
 * @since 2024/6/1 0:15
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Idempotent {
}
