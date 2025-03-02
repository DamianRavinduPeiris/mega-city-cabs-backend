package com.damian.megacity.controller;

import com.damian.megacity.exceptions.DistanceException;
import com.damian.megacity.response.Response;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.damian.megacity.service.constants.DistanceConstants.*;

@WebServlet(name = DISTANCE_CONTROLLER, value = DISTANCE_ENDPOINT)
@Log
public class DistanceController extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var origins = request.getParameter(ORIGINS_PARAM);
        var destinations = request.getParameter(DESTINATIONS_PARAM);
        var apiKey = System.getenv(GOOGLE_API_KEY_ENV);

        if (apiKey == null) {
            log.warning(API_KEY_MISSING_MSG);
            throw new DistanceException(API_KEY_MISSING_MSG);
        }

        var requestUrl = String.format(API_URL_TEMPLATE, origins, destinations, apiKey);

        var jsonResponse = callGoogleMapsAPI(requestUrl);

        response.getWriter().println(gson.toJson(createAndBuildResponse(
                HttpServletResponse.SC_OK,
                "Distance calculated successfully!",
                gson.fromJson(jsonResponse, Object.class)
        )));
    }

    private String callGoogleMapsAPI(String requestUrl) throws IOException {
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Response: " + response.body());
            return response.body();
        } catch (InterruptedException e) {
            log.warning(String.format(API_CALL_ERROR_MSG, e.getMessage()));
            throw new DistanceException(String.format(API_CALL_ERROR_MSG, e.getMessage()));
        }
    }

    public Response createAndBuildResponse(int status, String msg, Object data) {
        return Response.builder()
                .status(status)
                .message(msg)
                .data(data)
                .build();
    }
}
