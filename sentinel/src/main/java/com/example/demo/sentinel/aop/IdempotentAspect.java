package com.example.demo.sentinel.aop;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.CommonResult;
import com.example.demo.api.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangtao
 * @since 2024/6/1 0:15
 */
@Aspect
@Component
public class IdempotentAspect {
    @Pointcut("@annotation(com.example.demo.sentinel.aop.Idempotent)")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();

        //通过setnx检查redis中是否存在requestId，如果设置失败，从redis中取出数据
        if("123".equals(request.getHeader("requestId"))){
            User user=new User();
            user.setId(1L);
            user.setName("zhangsan");

            return CommonResult.success(JSON.toJSONString(user));
        }
        return joinPoint.proceed();
    }
}
