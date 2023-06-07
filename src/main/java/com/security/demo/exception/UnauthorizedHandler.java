package com.security.demo.exception;

import com.security.demo.base.ErrorMessage;
import com.security.demo.base.ResponseMessage;
import com.security.demo.base.util.JsonStringConverter;
import com.security.demo.base.util.TimezoneUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int statusCode = HttpStatus.UNAUTHORIZED.value();
        PrintWriter out = response.getWriter();
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(statusCode)
                .date(LocalDateTime.now(TimezoneUtil.getZoneIdJakarta()))
                .message(ResponseMessage.AUTH_IS_INVALID)
                .uri(authException.getMessage())
                .build();
        String err = JsonStringConverter.toJsonString(errorMessage);

        out.print(err);
        out.close();
    }
}
