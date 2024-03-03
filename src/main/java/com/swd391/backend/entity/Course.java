package com.swd391.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;
    @Column(name = "course_name", length = 50)
    private String CourseName;
    @Column(name = "description", columnDefinition = "TEXT")
    private String Description;
    @Column(name = "price")
    private double Price;
    @Column(name = "amount")
    private int Amount;

    //Relationship
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "course_category",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    List<Category> Categories;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Course", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JsonIgnore
    private List<OrderDetail> OrderDetails;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Course", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE
    })
    @JsonIgnore
    List<Image> Image;
    //private List<Chapter> Chapters;
}
