package com.swd391.backend.service.Interface;

import com.swd391.backend.entity.Order;
import com.swd391.backend.request.CreateOrder;

import java.util.List;

public interface IOrderService {
    Order CreateOrderCart(List<CreateOrder> orders, String username);

    Object GetOrderCar(String username);

    Object GetOrderDetail(String orderID, String s);

    Object PayOrder(String orderID, String s);

    void DeleteOrder(String orderID, String s);
}
