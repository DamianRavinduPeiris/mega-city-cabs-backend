package com.damian.megacity.util.filters;

import com.damian.megacity.exceptions.UserAlreadyExistsException;
import com.damian.megacity.exceptions.UserNotFoundException;
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
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            handleException(e, httpServletResponse);
        }
    }

    @Override
    public void destroy() {
        log.info("destroy() invoked!");
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var message = switch (e) {
            case UserAlreadyExistsException ex -> {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                yield "Bad Request : " + ex.getMessage();
            }
            case UserNotFoundException ex -> {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                yield "User Not Found : " + ex.getMessage();
            }
            default -> {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                yield "Internal Server Error";
            }
        };

        response.getWriter().println("{\"error\": \"" + message + "\"}");
    }
}
