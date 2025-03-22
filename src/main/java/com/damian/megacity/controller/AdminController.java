package com.damian.megacity.controller;

import com.damian.megacity.response.Response;
import com.damian.megacity.service.AdminService;
import com.damian.megacity.service.impl.AdminServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;


import static com.damian.megacity.service.constants.AdminConstants.*;

@WebServlet(name = ADMIN_CONTROLLER, value = ADMIN_ENDPOINT)
@Log
public class AdminController extends HttpServlet {
    private final transient Gson gson = new Gson();
    private final transient AdminService adminService = new AdminServiceImpl();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        var username = request.getParameter(ADMIN_USERNAME);
        var password = request.getParameter(ADMIN_PASSWORD);

        if (username != null && password != null) {
            var authStatus = adminService.searchByUsernameAndAuthenticate(username, password);
            try {
                response.getWriter().println(gson.toJson(createAndBuildResponse(
                        authStatus ? HttpServletResponse.SC_OK : HttpServletResponse.SC_UNAUTHORIZED,
                        authStatus ? ADMIN_AUTHENTICATED : WRONG_CREDENTIALS,
                        authStatus)));
            }catch (Exception e){
                log.warning(e.getMessage());
            }

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
