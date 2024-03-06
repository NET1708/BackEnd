package com.swd391.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;
    @Column(name = "name", length = 256)
    private String imageName;
    @Column(name = "is_icon")
    private boolean isIcon;
    @Column(name = "url", length = 256)
    private String url;
    @Column(name = "image_data", columnDefinition = "LONGTEXT")
    @Lob
    private String imageData;
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
