package com.cetc.web.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

//@Component
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Timefilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Timefilter start");
        long start = new Date().getTime();
        chain.doFilter(request,response);  //处理耗时
        System.out.println("Timefilter :"+(new Date().getTime()-start));
        System.out.println("Timefilter end");
    }

    @Override
    public void destroy() {
        System.out.println("Timefilter destroy");
    }
}
