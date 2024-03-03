package com.swd391.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;
    @Column(name = "created_at")
    private Date CreatedAt;
    @Column(name = "total")
    private double Total;
    @Column(name = "status")
    private String Status;

    //Relationship
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id", nullable = false)
    private User User;
    @OneToMany(mappedBy = "Order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetail> OrderDetail;
}
