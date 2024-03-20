package com.swd391.backend.dao;

import com.swd391.backend.entity.Course;
import com.swd391.backend.entity.Image;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@Tag(name = "Image", description = "Image management APIs")
@RepositoryRestResource(path = "image")
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findImageByCourse(Course course);
}
