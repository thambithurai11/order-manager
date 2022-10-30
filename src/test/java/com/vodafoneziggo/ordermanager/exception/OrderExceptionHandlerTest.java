//package com.vodafoneziggo.ordermanager.exception;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
//public class OrderExceptionHandlerTest {
//
//    @InjectMocks
//    private OrderExceptionHandler orderExceptionHandler;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @BeforeEach
//    public void setUp() {
//        when(request.getRequestURI()).thenReturn("/v1/orders");
//    }
//
//    @Test
//    public void testHandleException() {
//
//
//        ResponseEntity<?> response = orderExceptionHandler.handleException(
//                new OrderException(OrderErrorCodes.NOT_FOUND, ""),
//                request);
//
//    }
//}
