package com.swd391.backend.dao;

import com.swd391.backend.entity.Order;
import com.swd391.backend.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@Tag(name = "Order", description = "Order management APIs")
@RepositoryRestResource(path = "order")
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
