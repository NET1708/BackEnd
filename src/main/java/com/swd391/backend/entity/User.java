package com.swd391.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "student_code", length = 10)
    private String studentCode;
    @Column(name = "username", length = 50)
    private String username;
    @Column(name = "password", length = 50)
    private String password;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "full_name", length = 50)
    private String fullName;
    @Column(name = "address", length = 50)
    private String address;
    @Column(name = "phone", length = 50)
    private String phone;
    @Column(name = "avatar", length = 50)
    private String avatar;
    //Relationship
    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JsonIgnore
    private List<Order> orders;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roles;
}
