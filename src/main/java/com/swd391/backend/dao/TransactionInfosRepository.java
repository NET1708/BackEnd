package com.swd391.backend.dao;

import com.swd391.backend.entity.TransactionInfos;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "Transaction of TpBank", description = "Transaction management TPBank APIs")
@RepositoryRestResource(path = "transactions")
public interface TransactionInfosRepository extends JpaRepository<TransactionInfos, String> {
}
