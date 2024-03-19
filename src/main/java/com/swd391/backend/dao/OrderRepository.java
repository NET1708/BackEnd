package com.swd391.backend.dao;

import com.swd391.backend.entity.Order;
import com.swd391.backend.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Tag(name = "Order", description = "Order management APIs")
@RepositoryRestResource(path = "order")
@Transactional
public interface OrderRepository extends JpaRepository<Order, String> {
    Order getOrdersByUserAndStatus(User user, int status);
    void deleteOrderByOrderId(String orderId);
}
