package com.swd391.backend.service;

import com.swd391.backend.dao.OrderRepository;
import com.swd391.backend.dao.UserRepository;
import com.swd391.backend.entity.Order;
import com.swd391.backend.request.CreateOrder;
import com.swd391.backend.service.Interface.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void CreateOrderCart(List<CreateOrder> orders, String username) {

    }

    @Override
    public List<Order> GetOrderCar(String username) {
        return orderRepository.getOrdersByUserAndStatus(userRepository.findByUsername(username), 0);

    }
}
