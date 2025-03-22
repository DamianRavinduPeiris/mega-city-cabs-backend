package com.damian.megacity.controller;

import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.response.Response;
import com.damian.megacity.service.RideBookingService;
import com.damian.megacity.service.impl.RideBookingServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.damian.megacity.service.constants.RideBookingConstants.*;
import static com.damian.megacity.service.constants.UserConstants.USER_EMAIL;

@WebServlet(name = RIDE_BOOKING_CONTROLLER, value = RIDE_BOOKING_ENDPOINT)
@Log
public class RideBookingController extends HttpServlet {
    private final transient Gson gson = new Gson();
    private final transient RideBookingService<RideBookingDTO> rideBookingService = new RideBookingServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            var userEmail = request.getParameter(USER_EMAIL);
            var rideBookingDTO = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), RideBookingDTO.class);

            gson.toJson(createAndBuildResponse(HttpServletResponse.SC_CREATED,
                    BOOKING_ADDED_SUCCESSFULLY,
                    rideBookingService.add(rideBookingDTO, userEmail)), response.getWriter());

        } catch (Exception e) {
            log.warning(e.getMessage());
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            gson.toJson(createAndBuildResponse(HttpServletResponse.SC_CREATED,
                    BOOKINGS_RETRIEVED_SUCCESSFULLY,
                    rideBookingService.getAll()), response.getWriter());
        } catch (Exception e) {
            log.warning(e.getMessage());
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
