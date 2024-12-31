package com.Food.Ordering.System.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws java.io.IOException, jakarta.servlet.ServletException {
        response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Access Denied");
    }
}
