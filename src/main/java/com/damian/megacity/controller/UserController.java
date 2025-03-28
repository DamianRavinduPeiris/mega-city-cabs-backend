package com.damian.megacity.controller;

import java.io.*;
import java.util.stream.Collectors;

import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.exceptions.UserException;
import com.damian.megacity.response.Response;
import com.damian.megacity.service.CabService;
import com.damian.megacity.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.extern.java.Log;

import static com.damian.megacity.service.constants.UserConstants.*;


@WebServlet(name = USER_CONTROLLER, value = USER_ENDPOINT)
@Log
public class UserController extends HttpServlet {
    private final transient Gson gson = new Gson();
    private final transient CabService<UserDTO> userService = new UserServiceImpl();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            var user = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), UserDTO.class);

            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_CREATED,
                    USER_CREATED,
                    userService.add(user))));
        } catch (Exception e) {
            log.warning(e.getMessage());
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            var user = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), UserDTO.class);

            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_CREATED,
                    USER_UPDATED,
                    userService.update(user))));
        } catch (Exception e) {
            log.warning(e.getMessage());
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            var userId = request.getParameter(USER_ID);
            if (userId != null) {
                userService.delete(userId);
            }
            response.getWriter().println(gson.toJson(createAndBuildResponse(
                    HttpServletResponse.SC_NO_CONTENT,
                    USER_DELETED,
                    null)));
        } catch (Exception e) {
            log.warning(e.getMessage());
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            var userId = request.getParameter(USER_ID);

            if (userId != null) {
                response.getWriter().println(gson.toJson(createAndBuildResponse(
                        HttpServletResponse.SC_OK,
                        USER_RETRIEVED,
                        userService.search(userId))));
            } else {
                response.getWriter().println(gson.toJson(createAndBuildResponse(
                        HttpServletResponse.SC_OK,
                        ALL_USERS_RETRIEVED,
                        userService.getAll())));
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
            throw new UserException(e.getMessage());
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