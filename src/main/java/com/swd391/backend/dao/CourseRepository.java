package com.swd391.backend.dao;

import com.swd391.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "course")
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
