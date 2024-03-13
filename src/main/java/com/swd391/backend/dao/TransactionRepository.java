package com.swd391.backend.dao;

import com.swd391.backend.entity.Transaction;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "Transaction", description = "Transaction management APIs")
@RepositoryRestResource(path = "transaction")
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
