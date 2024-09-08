package com.example.customer.service;

import com.example.customer.model.Order;

import java.util.List;

public interface OrderService
{
    Order saveOrder(Order order);
    List<Order> getAllOrder();
    Order getOrderById(long id);
    Order updateOrder(Order order,long id);
    void deleteOrder(long id);
    void createInstallmentsForOrder(long orderId);
}
