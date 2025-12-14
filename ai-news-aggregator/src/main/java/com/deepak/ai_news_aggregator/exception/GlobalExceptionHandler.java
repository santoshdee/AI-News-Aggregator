package com.deepak.ai_news_aggregator.exception;

import com.deepak.ai_news_aggregator.dto.NewsErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<NewsErrorResponse> handleParseException(
            ParseException ex,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid format",
                request
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<NewsErrorResponse> handleBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<NewsErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON request",
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<NewsErrorResponse> handleGenericException(
        Exception ex,
        HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request
        );
    }

    private ResponseEntity<NewsErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(status).body(
                NewsErrorResponse.builder()
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(message)
                        .path(request.getRequestURI())
                        .timestamp(Instant.now())
                        .build()
        );
    }
}
