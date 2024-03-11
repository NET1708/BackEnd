package com.swd391.backend.service.Interface;

import com.swd391.backend.entity.Category;
import com.swd391.backend.entity.Chapter;
import com.swd391.backend.entity.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICourseService {
    int addCourse(String chapter, List<Integer> category, String courseName, String courseDescription, int coursePrice);
}
