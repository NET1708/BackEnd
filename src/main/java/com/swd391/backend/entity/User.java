package com.swd391.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;
    @Column(name = "student_code", length = 10)
    private String StudentCode;
    @Column(name = "username", length = 50)
    private String Username;
    @Column(name = "password", length = 50)
    private String Password;
    @Column(name = "email", length = 50)
    private String Email;
    @Column(name = "full_name", length = 50)
    private String FullName;
    @Column(name = "address", length = 50)
    private String Address;
    @Column(name = "phone", length = 50)
    private String Phone;
    @Column(name = "avatar", length = 50)
    private String Avatar;
    //Relationship
    @OneToMany(mappedBy = "User", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    private List<Order> Orders;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> Roles;
}
