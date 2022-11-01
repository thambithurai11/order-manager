package com.vodafoneziggo.ordermanager.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link OrderExceptionHandler}
 *
 * @author Thambi Thurai Chinnadurai
 */
@ExtendWith(SpringExtension.class)
public class OrderExceptionHandlerTest {

    @InjectMocks
    private OrderExceptionHandler orderExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        when(request.getRequestURI()).thenReturn("/v1/orders");
    }

    /**
     * Test case for handling exceptions with OrderErrorCodes.NOT_FOUND errors
     */
    @Test
    void testHandleException_WithOrderExceptionNotFound() {

        ResponseEntity<?> response = orderExceptionHandler.exceptionHandler(
                new OrderException(OrderErrorCodes.NOT_FOUND, ""),
                request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test case for handling exceptions with OrderErrorCodes.INVALID_ORDER errors
     */
    @Test
    void testHandleException_WithOrderExceptionInvalidOrder() {

        ResponseEntity<?> response = orderExceptionHandler.exceptionHandler(
                new OrderException(OrderErrorCodes.INVALID_ORDER, ""),
                request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Test case for handling exceptions with OrderErrorCodes.UNKNOWN errors
     */
    @Test
    void testHandleException_WithOrderException() {

        ResponseEntity<?> response = orderExceptionHandler.exceptionHandler(
                new OrderException(OrderErrorCodes.UNKNOWN, ""),
                request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Test case for handling exceptions with Other errors
     */
    @Test
    void testHandleException_WithOtherException() {

        ResponseEntity<?> response = orderExceptionHandler.exceptionHandler(
                new RuntimeException(),
                request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
