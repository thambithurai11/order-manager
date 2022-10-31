package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.db.entity.Orders;
import com.vodafoneziggo.ordermanager.model.OrderRequest;

import java.util.List;

public interface OrderService {

    public Orders createOrder(OrderRequest order);

    public List<Orders> getAllOrders(int page, int size);
}
