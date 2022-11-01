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
 * OrderExceptionHandler to handle the exceptions in the application and
 * map them into Application custom Error {@link OrderError} with {@link OrderErrorCodes}
 *
 * @author Thambi Thurai Chinnadurai
 */
@Slf4j
@ControllerAdvice
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<OrderError> exceptionHandler(Exception exception, HttpServletRequest request) {

        if (exception instanceof OrderException) {

            OrderException orderException = (OrderException) exception;
            switch (orderException.getErrorCode()) {
                case NOT_FOUND:
                    return new ResponseEntity<>(
                            new OrderError(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()),
                            HttpStatus.NOT_FOUND);
                case INVALID_ORDER:
                    return new ResponseEntity<>(
                            new OrderError(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getRequestURI()),
                            HttpStatus.BAD_REQUEST);
                default:
                    return new ResponseEntity<>(
                            new OrderError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
                                           request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.error("CAUSE : {}", ExceptionUtils.getStackTrace(exception));
            return new ResponseEntity<>(
                    new OrderError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
                                   request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
