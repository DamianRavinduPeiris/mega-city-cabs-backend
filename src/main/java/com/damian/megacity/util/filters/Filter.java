package com.damian.megacity.util.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
@WebFilter(urlPatterns = {"/*"})
public class Filter implements jakarta.servlet.Filter {
    private static final String ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    private static final String METHODS = "GET, PUT, POST, DELETE";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ANY_ORIGIN = "*";

    public Filter() {
        log.info("Initializing the Filter...");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("init() invoked!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("doFilter() invoked!");

        var httpServletRequest = (HttpServletRequest) servletRequest;
        var httpServletResponse = (HttpServletResponse) servletResponse;

        httpServletResponse.setHeader(ALLOW_ORIGIN, ANY_ORIGIN);
        httpServletResponse.setHeader(ALLOW_METHODS, METHODS);
        httpServletResponse.setHeader(ALLOW_HEADERS, CONTENT_TYPE);
        httpServletResponse.setHeader(EXPOSE_HEADERS, CONTENT_TYPE);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("destroy() invoked!");
    }
}
