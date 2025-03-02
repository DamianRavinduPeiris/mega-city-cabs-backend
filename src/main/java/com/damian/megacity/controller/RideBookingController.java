package com.damian.megacity.controller;

import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.exceptions.RideBookingException;
import com.damian.megacity.response.Response;
import com.damian.megacity.service.RideBookingService;
import com.damian.megacity.service.impl.RideBookingServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.damian.megacity.service.constants.RideBookingConstants.*;

@WebServlet(name = RIDE_BOOKING_CONTROLLER, value = RIDE_BOOKING_ENDPOINT)
public class RideBookingController extends HttpServlet {
    private final Gson gson = new Gson();
    private final RideBookingService<RideBookingDTO> rideBookingService = new RideBookingServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            var rideBookingDTO = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), RideBookingDTO.class);

            gson.toJson(createAndBuildResponse(HttpServletResponse.SC_CREATED,
                    BOOKING_ADDED_SUCCESSFULLY,
                    rideBookingService.add(rideBookingDTO)), response.getWriter());

        } catch (IOException e) {
            throw new RideBookingException(AN_ERROR_OCCURRED_WHILE_ADDING_BOOKING);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            gson.toJson(createAndBuildResponse(HttpServletResponse.SC_CREATED,
                    BOOKINGS_RETRIEVED_SUCCESSFULLY,
                    rideBookingService.getAll()), response.getWriter());
        } catch (IOException e) {
            throw new RideBookingException(AN_ERROR_OCCURRED_WHILE_RETRIEVING_BOOKINGS);
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
