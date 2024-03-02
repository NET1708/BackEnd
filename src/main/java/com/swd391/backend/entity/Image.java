package com.swd391.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;
    @Column(name = "name", length = 256)
    private String Name;
    @Column(name = "is_icon")
    private boolean isIcon;
    @Column(name = "url", length = 256)
    private String URL;
    @Column(name = "image_data", columnDefinition = "LONGTEXT")
    @Lob
    private String ImageData;
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "course_id", nullable = false)
    private Course Course;
}
