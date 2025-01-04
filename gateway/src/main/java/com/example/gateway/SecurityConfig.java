package com.example.gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zhangtao
 * @since 2025/1/4 10:28
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 公开访问的路径
                .antMatchers("/public/**").permitAll()
                // 其他路径需要认证
                .anyRequest().authenticated()
                .and()
                .oauth2Login() // 使用 OAuth2 登录
                .and()
                .csrf().disable(); // 如果需要禁用 CSRF 防护，视需求而定
    }
}

