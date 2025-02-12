package com.damian.megacity.util.filters;

import com.damian.megacity.exceptions.DriverException;
import com.damian.megacity.exceptions.UserException;
import com.damian.megacity.response.Response;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
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
    private static final String APPLICATION_JSON = "application/json";
    private final Gson gson = new Gson();

    public Filter() {
        log.info("Initializing the Filter...");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("init() invoked!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        log.info("doFilter() invoked!");

        var httpServletResponse = (HttpServletResponse) servletResponse;

        httpServletResponse.setHeader(ALLOW_ORIGIN, ANY_ORIGIN);
        httpServletResponse.setHeader(ALLOW_METHODS, METHODS);
        httpServletResponse.setHeader(ALLOW_HEADERS, CONTENT_TYPE);
        httpServletResponse.setHeader(EXPOSE_HEADERS, CONTENT_TYPE);
        httpServletResponse.setHeader(CONTENT_TYPE, APPLICATION_JSON);
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
        response.setContentType(APPLICATION_JSON);

        var message = switch (e) {
            case UserException ex -> {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                yield "An Exception Occurred in the User Service : " + ex.getMessage();
            }
            case DriverException ex -> {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                yield "An Exception Occurred in the Driver Service : " + ex.getMessage();
            }
            default -> {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                yield "Internal Server Error";
            }
        };

        response.getWriter().println(gson.toJson(createAndBuildResponse(response.getStatus(), message, null)));
    }

    public Response createAndBuildResponse(int status, String msg, Object data) {
        return Response.builder()
                .status(status)
                .message(msg)
                .data(data)
                .build();
    }
}
