package com.vodafoneziggo.ordermanager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * OrderError class to represent exceptions handled by ControllerAdvice {@link OrderExceptionHandler}
 *
 * @author Thambi Thurai Chinnadurai
 */
@Getter
@RequiredArgsConstructor
public class OrderError {

    private final LocalDateTime timestamp = LocalDateTime.now();

    private final HttpStatus status;

    private final String message;

    private final String path;

}
