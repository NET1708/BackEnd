package com.swd391.backend.dao;

import com.swd391.backend.entity.OrderDetail;
import com.swd391.backend.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@Tag(name = "OrderDetail", description = "OrderDetail management APIs")
@RepositoryRestResource(path = "order-detail")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
