package com.booking.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/sign-in").setViewName("login");
        registry.addViewController("/sign-up").setViewName("register");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/account").setViewName("account");
        registry.addViewController("/preorder").setViewName("preorder");
        registry.addViewController("/checkout").setViewName("checkout");
        registry.addViewController("/payment").setViewName("payment");
        registry.addViewController("/error").setViewName("error");
    }
}
