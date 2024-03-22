package com.swd391.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter_id;
    private String name;
    private int duration;
    private LocalDate createAt;
    @ManyToOne
    @JoinColumn(name = "createBy")
    private User createBy;
    private float mark;
}
