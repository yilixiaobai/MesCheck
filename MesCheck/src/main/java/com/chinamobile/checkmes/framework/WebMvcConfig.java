package com.chinamobile.checkmes.framework;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import javax.annotation.Resource;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Resource
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor).addPathPatterns("/*").excludePathPatterns("/registerCom","/register","/lougout","/index","/getVerify","/checkVerify", "/getVerCode", "/login","/loginCompany","/forgetpassword","/editpassword","/editpass","/checkphone","/getcompVerCode","/editpasswordindex");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
