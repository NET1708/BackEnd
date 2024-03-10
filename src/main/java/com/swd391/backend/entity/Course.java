package com.swd391.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;
    @Column(name = "course_name", length = 50)
    private String courseName;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "average_rating")
    @JsonProperty("averageRating")
    @ColumnDefault("0")
    private Double averageRating;

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
    List<Category> categories;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JsonIgnore
    private List<OrderDetail> orderDetails;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE
    })
    @JsonIgnore
    List<Image> images;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    List<Rate> rates;
    //private List<Chapter> Chapters;

    public void updateAverageRating() {
        if (rates.isEmpty()) {
            averageRating = 0.0;
        } else {
            double sum = rates.stream().mapToDouble(Rate::getRate).sum();
            averageRating = sum / rates.size();
        }
    }
}
