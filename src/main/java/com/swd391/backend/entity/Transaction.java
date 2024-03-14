package com.swd391.backend.entity;

import jakarta.persistence.*;
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
    @OneToOne(mappedBy = "transaction")
    private Order order;
    // Getter và setter
}
