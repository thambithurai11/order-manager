package com.vodafoneziggo.ordermanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * OrderExceptionHandler
 * To handle expected exceptions in Application and Map them to Application specific {@link OrderErrorCodes} which are translated to {@link OrderError}
 */
@ControllerAdvice
@Slf4j
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> exceptionHandler(Exception exception, HttpServletRequest request) {

        if (exception instanceof OrderException) {

            OrderException orderException = (OrderException) exception;
            switch (orderException.getErrorCode()) {
                case NOT_FOUND:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new OrderError(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()));
                case INVALID_ORDER:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new OrderError(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getRequestURI()));
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                            new OrderError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
                                           request.getRequestURI()));
            }
        } else {
            log.error("CAUSE : {}", ExceptionUtils.getStackTrace(exception));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new OrderError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
                                   request.getRequestURI()));
        }
    }
}