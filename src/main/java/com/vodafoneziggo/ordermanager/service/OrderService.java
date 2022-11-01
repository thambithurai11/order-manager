package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.db.entity.Orders;
import com.vodafoneziggo.ordermanager.model.OrderRequest;

import java.util.List;

public interface OrderService {

    Orders createOrder(OrderRequest order);

    List<Orders> getAllOrders(int page, int size);
}
