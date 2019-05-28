package com.example.demo.config;


import com.example.demo.config.LoginHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sound.midi.Soundbank;

/**
 * @program: spring-boot-01-hello
 * @description: 这个config是为了地址栏直接访问8080可以到这个方法里面的地址。因为SpringBoot直接访问的是static下面的index页面。
 * @author: Lunatic Princess
 * @create: 2019-01-27
 * Detailed time at 10:49
 **/
//这个是配置类，需要这个注解进行标识
//@Configuration

public class MySpringMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/thymeleaf/userIndex/html")
                .excludePathPatterns("/static**");

        registry.addInterceptor(new BackLoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/thymeleaf/backAdminLogin/html")
                .excludePathPatterns("/static**");
    }
}
