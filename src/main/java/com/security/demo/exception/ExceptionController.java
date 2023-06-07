package com.security.demo.exception;

import com.security.demo.base.ErrorMessage;
import com.security.demo.base.util.TimezoneUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    protected ResponseEntity<Object> exceptionHandler(HttpStatus httpStatus, String uri, String message) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(httpStatus.value())
                .date(LocalDateTime.now(TimezoneUtil.getZoneIdJakarta()))
                .message(message)
                .uri(uri)
                .build();

        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return exceptionHandler(status, "", errors);
    }
    @ExceptionHandler(EntityExistsException.class)
    ResponseEntity<Object> entityExistsExceptionHandler(WebRequest request, EntityExistsException ex){
        return exceptionHandler(HttpStatus.CONFLICT, ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());
    }
    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<Object> nullPointerExceptionHandler(WebRequest request, NullPointerException ex){
        return exceptionHandler(HttpStatus.NOT_FOUND, ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());
    }
    @ExceptionHandler(AuthenticationServiceException.class)
    ResponseEntity<Object> authenticationExceptionHandler(WebRequest request, NullPointerException ex){
        return exceptionHandler(HttpStatus.UNAUTHORIZED, ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<Object> usernameNotFoundExceptionHandler(WebRequest request, NullPointerException ex){
        return exceptionHandler(HttpStatus.UNAUTHORIZED, ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());
    }
}
