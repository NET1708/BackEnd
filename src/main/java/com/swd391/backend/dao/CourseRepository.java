package com.swd391.backend.dao;

import com.swd391.backend.entity.Course;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "Course", description = "Course management APIs")
@RepositoryRestResource(path = "course")
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
