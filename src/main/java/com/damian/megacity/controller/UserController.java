package com.damian.megacity.controller;

import java.io.*;

import com.damian.megacity.dto.UserDTO;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.extern.java.Log;


@WebServlet(name = "userController", value = "/api/v1/user")
@Log
public class UserController extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var user = gson.fromJson(request.getReader().readLine(), UserDTO.class);
        log.info("User: " + user);
        response.getWriter().println(gson.toJson(user));

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(gson.toJson("Hello World!"));

    }
}