package com.damian.megacity.controller;

import com.damian.megacity.response.Response;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.damian.megacity.service.constants.HealthConstants.*;

@WebServlet(name = HEALTH_CONTROLLER, urlPatterns = HEALTH_ENDPOINT)
@Log
public class HealthCheckController extends HttpServlet {
    private static final String HEALTH_CHECK_URL = System.getenv(INVOKING_URL);
    private final transient ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final transient HttpClient client = HttpClient.newHttpClient();
    private final transient Gson gson = new Gson();

    @Override
    public void init() {
        scheduler.scheduleAtFixedRate(this::invokeHealthCheck, 0, 3, TimeUnit.MINUTES);
    }

    private void invokeHealthCheck() {
        try {
            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(HEALTH_CHECK_URL))
                    .build();

            var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("Health Check Response: " + response.body());
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            log.severe("Health check failed: " + e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        try {
            servletResponse
                    .getWriter()
                    .println(gson.toJson(createAndBuildResponse(200, HEALTH_STATUS_UP, null)));
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        scheduler.shutdown();
    }

    public Response createAndBuildResponse(int status, String msg, Object data) {
        return Response.builder()
                .status(status)
                .message(msg)
                .data(data)
                .build();
    }
}
