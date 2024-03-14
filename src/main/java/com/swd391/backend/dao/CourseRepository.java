package com.swd391.backend.dao;

import com.swd391.backend.entity.Course;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Tag(name = "Course", description = "Course management APIs")
@RepositoryRestResource(path = "course")
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Page<Course> findByCourseNameContaining(String courseName, Pageable pageable);
    Page<Course> findByCategories_categoryId(int categoryId, Pageable pageable);
    Page<Course> findByCourseNameContainingAndCategories_categoryId(String courseName, int categoryId, Pageable pageable);
    Course findByCourseId(int courseId);
    Course findTopByOrderByCourseIdDesc();
}
