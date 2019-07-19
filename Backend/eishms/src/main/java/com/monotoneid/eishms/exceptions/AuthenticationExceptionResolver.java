package com.monotoneid.eishms.exceptions;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationExceptionResolver {

    @ExceptionHandler(AuthenticationException.class)
    public HashMap<String, String> handleAuthenticationException(HttpServletRequest request, AuthenticationException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Error: Unathorized!");
        return response;
    }
}