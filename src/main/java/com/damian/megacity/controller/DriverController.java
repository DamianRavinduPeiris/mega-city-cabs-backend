package com.damian.megacity.controller;

import java.io.*;
import java.util.stream.Collectors;

import com.damian.megacity.dto.DriverDTO;
import com.damian.megacity.response.Response;
import com.damian.megacity.service.CabService;
import com.damian.megacity.service.impl.DriverServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.extern.java.Log;

import static com.damian.megacity.service.constants.DriverConstants.*;


@WebServlet(name = DRIVER_CONTROLLER, value = DRIVER_ENDPOINT)
@Log
public class DriverController extends HttpServlet {
    private final transient Gson gson = new Gson();
    private final transient CabService<DriverDTO> driverService = new DriverServiceImpl();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            var driver = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), DriverDTO.class);

            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_CREATED,
                    DRIVER_CREATED,
                    driverService.add(driver))));
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            var driver = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), DriverDTO.class);

            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_CREATED,
                    DRIVER_UPDATED,
                    driverService.update(driver))));
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            var driverId = request.getParameter(DRIVER_ID);
            if (driverId != null) {
                driverService.delete(driverId);
            }
            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_NO_CONTENT,
                    DRIVER_DELETED,
                    null)));
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  {
        try {
            var driverId = request.getParameter(DRIVER_ID);

            if (driverId != null) {
                response.getWriter().println(gson.toJson(createAndBuildResponse(
                        HttpServletResponse.SC_OK,
                        DRIVER_RETRIEVED,
                        driverService.search(driverId))));
            } else {
                response.getWriter().println(gson.toJson(createAndBuildResponse(
                        HttpServletResponse.SC_OK,
                        ALL_DRIVERS_RETRIEVED,
                        driverService.getAll())));
            }
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