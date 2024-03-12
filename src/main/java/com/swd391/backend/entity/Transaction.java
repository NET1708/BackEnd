package com.swd391.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    private String id;
    private String type;
    private String transactionID;
    private String amount;
    private String description;
    private String bank;

    // Getter và setter
}
