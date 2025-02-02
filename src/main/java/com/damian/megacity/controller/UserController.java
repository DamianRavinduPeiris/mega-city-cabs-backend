package com.damian.megacity.controller;

import java.io.*;
import java.util.stream.Collectors;

import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.response.Response;
import com.damian.megacity.service.UserService;
import com.damian.megacity.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.extern.java.Log;


@WebServlet(name = "userController", value = "/api/v1/user")
@Log
public class UserController extends HttpServlet {
    private final Gson gson = new Gson();
    private final UserService<UserDTO> userService = new UserServiceImpl();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var user = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), UserDTO.class);

        response.getWriter().println(gson.toJson(createAndBuildResponse(
                HttpServletResponse.SC_CREATED,
                "User Successfully created!",
                userService.add(user))));
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var user = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), UserDTO.class);
        response.getWriter().println(gson.toJson(userService.update(user)));
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var user = gson.fromJson(request.getReader().lines().collect(Collectors.joining()), UserDTO.class);
        userService.delete(user);
//        response.getWriter().println(gson.toJson());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(gson.toJson("Hello World!"));

    }

    public Response createAndBuildResponse(int status, String msg, Object data) {
        return new Response()
                .setStatus(status)
                .setMessage(msg)
                .setData(data)
                .build();
    }
}