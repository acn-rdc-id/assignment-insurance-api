package com.azid.auth.backend.AZ.Auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
        private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
                ForbiddenException.class, HttpStatus.FORBIDDEN,
                ResourceNotFoundException.class, HttpStatus.NOT_FOUND,
                BadRequestException.class, HttpStatus.BAD_REQUEST
        );

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse<?>> handleException(Exception ex) {
            HttpStatusCode status = EXCEPTION_STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

            ErrorResponse<Object> errorResponse = new ErrorResponse<>(
                    "Failure",
                    status.value(),
                    ex.getMessage(),
                    Map.of("error", ex.getMessage())
            );

            return new ResponseEntity<>(errorResponse, status);
        }
}


