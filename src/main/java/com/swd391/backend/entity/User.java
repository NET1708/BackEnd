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
    @Column(name = "password")
    private String password;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "full_name", length = 50)
    private String fullName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone", length = 50)
    private String phone;
    @Column(name = "avatar", length = 50)
    private String avatar;
    @Column(name = "gender", length = 50)
    private char gender;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "activation_code", length = 50)
    private String activationCode;
    //Relationship
    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    private List<Rate> rates;
    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JsonIgnore
    private List<Order> orders;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roles;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "enrolled_course",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnore
    List<Course> courses;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
