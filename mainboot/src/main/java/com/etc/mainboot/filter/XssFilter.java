package com.etc.mainboot.filter;

import org.springframework.boot.SpringBootConfiguration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义过滤器，对请求request进行xss过滤处理
 * @author
 * @date 2019/6/21 0021
 */
@SpringBootConfiguration
@WebFilter(filterName = "XssFilter",urlPatterns = {"/*"})
public class XssFilter implements Filter {

    /**无需过滤xss的uri*/
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/pay/wxNotify","/pay/alNotify","/pay/gateway")));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");

        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if(allowedPath) {
            filterChain.doFilter(request, response);
        }else {
            filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
        }
    }

    @Override
    public void destroy() {

    }
}
