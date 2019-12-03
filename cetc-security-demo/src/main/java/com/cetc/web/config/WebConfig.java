package com.cetc.web.config;

import com.cetc.web.filter.TimeFilter;
import com.cetc.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    //异步方法配置
//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//        //可配置超时时间、拦截器、线程池
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(10));
//        configurer.setTaskExecutor(threadPoolTaskExecutor());
//    }
//
//    @Bean
//    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
//        t.setCorePoolSize(10);
//        t.setMaxPoolSize(50);
//        t.setThreadNamePrefix("order-");
//        return t;
//    }
//    @Bean
//    public FilterRegistrationBean timeFilter(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        TimeFilter timeFilter = new TimeFilter();
//        filterRegistrationBean.setFilter(timeFilter);
//        List<String> urls = new ArrayList<>();
//        urls.add("/*");
//        filterRegistrationBean.setUrlPatterns(urls);
//        return filterRegistrationBean;
//    }

//    @Autowired
//    TimeInterceptor timeInterceptor;

    //注册mvc interceptor
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(timeInterceptor);
//    }



}
