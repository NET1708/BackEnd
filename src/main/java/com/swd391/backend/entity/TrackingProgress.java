package com.swd391.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tracking")
public class TrackingProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course_id;
    @OneToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter_id;
    private float progress;
}
