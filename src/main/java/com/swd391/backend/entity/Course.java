package com.swd391.backend.entity;

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

    //Relationship
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Course", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    private List<OrderDetail> OrderDetails;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Course", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE
    })
    List<Image> Image;
    //private List<Chapter> Chapters;
}
