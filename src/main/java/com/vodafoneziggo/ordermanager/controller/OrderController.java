package com.vodafoneziggo.ordermanager.controller;


import com.vodafoneziggo.ordermanager.db.entities.Orders;
import com.vodafoneziggo.ordermanager.model.OrderRequest;
import com.vodafoneziggo.ordermanager.exception.OrderErrorCodes;
import com.vodafoneziggo.ordermanager.exception.OrderException;
import com.vodafoneziggo.ordermanager.service.OrderServiceImpl;
import com.vodafoneziggo.ordermanager.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * OrderController
 * Controller to serve all request coming for order
 */
@RestController
@RequestMapping(path = Constants.VERSION)
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    /**
     * Create Order for given order request
     *
     * @param orderRequest
     * @param errors
     * @return Created Order
     */
    @PostMapping(Constants.ORDERS)
    public ResponseEntity<Orders> createOrder(@RequestBody @Valid OrderRequest orderRequest, Errors errors) {

        if (errors.hasErrors() && errors.getFieldError() != null) {
            throw new OrderException(OrderErrorCodes.INVALID_ORDER,
                                     errors.getFieldError().getField() + ":" + errors.getFieldError().getDefaultMessage());
        }
        Orders orderCreated = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderCreated);
    }

    /**
     * Gets all the order from database
     *
     * @param page page defaults to 0
     * @param size size of page defaults to 10
     * @return List of all orders in page
     */
    @GetMapping(Constants.ORDERS)
    public ResponseEntity<List<Orders>> getAllOrders(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        List<Orders> allOrders = orderService.getAllOrders(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(allOrders);
    }
}
