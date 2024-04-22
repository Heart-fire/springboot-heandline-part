package com.atguigu.confin;

import com.atguigu.interceptors.LoginProtectedInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginProtectedInterceptor loginProtectedInterceptor;
//------------------------------蓝色被追踪过，但是又修改了---------------------------------
//------------------------------蓝色被追踪过，但是又修改了---------------------------------
//------------------------------蓝色被追踪过，但是又修改了---------------------------------
//    third commit
//    master commit
//    hot-fix commit
//    hot-fix commit3
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginProtectedInterceptor).addPathPatterns("/headline/**");
    }
}
