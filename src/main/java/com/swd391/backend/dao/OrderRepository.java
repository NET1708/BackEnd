package com.swd391.backend.dao;

import com.swd391.backend.entity.Order;
import com.swd391.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "order")
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
