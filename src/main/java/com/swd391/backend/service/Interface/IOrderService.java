package com.swd391.backend.service.Interface;

import com.swd391.backend.entity.Order;
import com.swd391.backend.request.CreateOrder;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {
    Order CreateOrderCart(CreateOrder orders, String username);

    Object GetOrderCar(String username);

    Object GetOrderDetail(String orderID, String s);

    Object PayOrder(String orderID, String s);

    void DeleteOrder(String orderID, String s);
    ResponseEntity<?> HandleOrderPayment(String orderID, int courseID, String token);
    ResponseEntity<?> ListEnrollCourse(String token);
}
