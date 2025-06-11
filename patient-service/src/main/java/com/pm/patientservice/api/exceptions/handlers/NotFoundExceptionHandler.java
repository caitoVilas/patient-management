package com.pm.patientservice.api.exceptions.handlers;

import com.pm.patientservice.api.exceptions.customs.NotFoundException;
import com.pm.patientservice.api.models.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Exception handler for NotFoundException.
 * This class handles exceptions of type NotFoundException and returns a structured error response.
 * It uses @RestControllerAdvice to apply globally to all controllers.
 *
 * @author Caito
 */
@RestControllerAdvice
public class NotFoundExceptionHandler {
    /**
     * Handles NotFoundException and returns a ResponseEntity with an ErrorResponse.
     *
     * @param ex      the NotFoundException that was thrown
     * @param request the HttpServletRequest that triggered the exception
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .method(request.getMethod())
                        .path(request.getRequestURL().toString())
                        .build()
        );
    }
}
