package com.swd391.backend.service;

import com.swd391.backend.dao.CourseRepository;
import com.swd391.backend.dao.OrderDetailRepository;
import com.swd391.backend.dao.OrderRepository;
import com.swd391.backend.dao.UserRepository;
import com.swd391.backend.entity.Course;
import com.swd391.backend.entity.Order;
import com.swd391.backend.entity.OrderDetail;
import com.swd391.backend.entity.User;
import com.swd391.backend.request.CreateOrder;
import com.swd391.backend.service.Interface.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository detailRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public Order CreateOrderCart(CreateOrder orders, String username) {
        Order order = GetOrderCar(username);
        Course course = null;
        if(order != null){
            course = courseRepository.findByCourseId(orders.getCourseID());
            order.setTotal(order.getTotal() + course.getPrice());
            detailRepository.save(OrderDetail.builder()
                    .price(course.getPrice())
                    .course(course)
                    .order(order)
                    .build());
            return orderRepository.save(order);
        }
        else{
            Date date = new Date();
            order = orderRepository.save(
                    Order.builder()
                            .orderId(GenerateOrderID(6))
                            .createdAt(date)
                            .total(0)
                            .status(0)
                            .user(userRepository.findByUsername(username))
                            .build()
            );
            course = courseRepository.findByCourseId(orders.getCourseID());
            order = orderRepository.findById(order.getOrderId()).get();
            order.setTotal(order.getTotal() + course.getPrice());
            detailRepository.save(OrderDetail.builder()
                    .price(course.getPrice())
                    .course(course)
                    .order(order)
                    .build());
            return orderRepository.save(order);
        }



    }

    @Override
    public Order GetOrderCar(String username) {
        return orderRepository.getOrdersByUserAndStatus(userRepository.findByUsername(username), 0);

    }

    @Override
    public List<OrderDetail> GetOrderDetail(String orderID, String s) {
        Order order = orderRepository.findById(orderID).orElse(null);
        return detailRepository.findAllOrderDetailByOrder(order);
    }

    @Override
    public Object PayOrder(String orderID, String s) {
        if(userRepository.existsByUsername(s)){
            Order existing = orderRepository.findById(orderID).get();
            existing.setStatus(1);

            return orderRepository.save(existing);
        }else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void DeleteOrder(String orderID, String s){
        Order order= orderRepository.findById(orderID).get();
        if(order.getStatus() != 1){
            orderRepository.deleteOrderByOrderId(orderID);
        }

    }

    public String GenerateOrderID(int length){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'A');
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}
