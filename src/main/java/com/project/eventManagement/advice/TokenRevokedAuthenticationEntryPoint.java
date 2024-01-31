package com.project.eventManagement.advice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenRevokedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter out = response.getWriter();

        ArrayList<String> errorMessage = new ArrayList<>();
        errorMessage.add("Your session has expired. Please login to continue");

        ErrorResponse errorResponse = new ErrorResponse(401, "Unauthorized", errorMessage);
        out.println(objectMapper.writeValueAsString(errorResponse));

    }

}
