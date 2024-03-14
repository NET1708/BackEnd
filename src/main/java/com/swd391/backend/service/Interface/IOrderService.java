package com.swd391.backend.service.Interface;

import com.swd391.backend.request.CreateOrder;

import java.util.List;

public interface IOrderService {
    void CreateOrderCart(List<CreateOrder> orders, String username);

    Object GetOrderCar(String username);
}
