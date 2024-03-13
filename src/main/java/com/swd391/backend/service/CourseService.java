package com.swd391.backend.service;

import com.swd391.backend.dao.CategoryRepository;
import com.swd391.backend.dao.ChapterRepository;
import com.swd391.backend.dao.CourseRepository;
import com.swd391.backend.entity.Category;
import com.swd391.backend.entity.Chapter;
import com.swd391.backend.entity.Course;
import com.swd391.backend.service.Interface.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public int addCourse(String chapter, List<Integer> category, String courseName, String courseDescription, int coursePrice) {
        // Create course
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDescription(courseDescription);
        course.setPrice(coursePrice);
        // Add course to database
        Course newCourse = courseRepository.save(course);


        // Add categories to database
        for (int categoryId : category) {
            Category cat = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Không tìm thấy Category với id: " + categoryId));
            cat.getCourses().add(newCourse);
            categoryRepository.save(cat);
        }
        return newCourse.getCourseId();
    }
}
