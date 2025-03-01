package com.damian.megacity.controller;

import java.io.IOException;

import com.damian.megacity.dto.VehicleDTO;
import com.damian.megacity.exceptions.VehicleException;
import com.damian.megacity.repo.VehicleDAOService;
import com.damian.megacity.repo.VehicleRepo;
import com.damian.megacity.response.Response;
import com.damian.megacity.service.CabService;
import com.damian.megacity.service.VehicleService;
import com.damian.megacity.service.impl.VehicleServiceImpl;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.damian.megacity.service.impl.constants.VehicleConstants.*;

@WebServlet(name = VEHICLE_CONTROLLER, urlPatterns = VEHICLE_ENDPOINT)
@MultipartConfig(maxFileSize = 10 * 1024 * 1024)
public class VehicleController extends HttpServlet {

    private final Gson gson = new Gson();
    private final VehicleDAOService vehicleDAOService = new VehicleRepo();
    private final VehicleService vehicleService = new VehicleServiceImpl(vehicleDAOService, vehicleDAOService);
    private final CabService<VehicleDTO> cabService = new VehicleServiceImpl(vehicleDAOService, vehicleDAOService);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var filePart = request.getPart(VEHICLE_IMAGE);
        var vehicleDTO = new VehicleDTO(request.getParameter(VEHICLE_ID),
                request.getParameter(VEHICLE_NAME),
                request.getParameter(VEHICLE_MAKE_YEAR),
                request.getParameter(VEHICLE_NUMBER_PLATE),
                null);

        if (filePart == null || filePart.getSize() == 0) {
            throw new VehicleException(VEHICLE_NOT_FOUND);
        }

        response.getWriter().println(gson.toJson(createAndBuildResponse(
                HttpServletResponse.SC_CREATED,
                VEHICLE_CREATED,
                vehicleService.addVehicleWithImages(vehicleDTO, filePart))));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var vehicleId = request.getParameter(VEHICLE_ID);

        if (vehicleId != null) {
            var vehicle = cabService.search(vehicleId);
            if (vehicle == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println(gson.toJson(createAndBuildResponse(
                        HttpServletResponse.SC_NOT_FOUND,
                        VEHICLE_NOT_FOUND,
                        null)));
            } else {
                response.getWriter().println(gson.toJson(createAndBuildResponse(
                        HttpServletResponse.SC_OK,
                        VEHICLE_RETRIEVED,
                        vehicle)));
            }
        } else {
            var vehicles = cabService.getAll();
            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_OK,
                    ALL_VEHICLES_RETRIEVED,
                    vehicles)));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var vehicleDTO = new VehicleDTO(request.getParameter(VEHICLE_ID),
                request.getParameter(VEHICLE_NAME),
                request.getParameter(VEHICLE_MAKE_YEAR),
                request.getParameter(VEHICLE_NUMBER_PLATE),
                null);

        var updatedVehicle = cabService.update(vehicleDTO);
        if (updatedVehicle == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    VEHICLE_NOT_FOUND,
                    null)));
        } else {
            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_OK,
                    VEHICLE_UPDATED,
                    updatedVehicle)));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        var vehicleId = request.getParameter(VEHICLE_ID);

        if (vehicleId == null || vehicleId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    VEHICLE_NOT_FOUND,
                    null)));
            return;
        }

        cabService.delete(vehicleId);
        response.getWriter().println(gson.toJson(createAndBuildResponse(
                HttpServletResponse.SC_OK,
                VEHICLE_DELETED,
                null)));
    }

    public Response createAndBuildResponse(int status, String msg, Object data) {
        return Response.builder()
                .status(status)
                .message(msg)
                .data(data)
                .build();
    }
}
