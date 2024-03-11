package com.swd391.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swd391.backend.entity.Course;
import com.swd391.backend.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Course", description = "Course management APIs")
@RequestMapping("/course")
@CrossOrigin(origins = "http://localhost:3000, https://api.ani-testlab.edu.vn")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody Map<String, Object> requestData) {
        String chapterData = (String) requestData.get("chapter_content");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> categoryIds = objectMapper.convertValue(requestData.get("categories"), List.class);
        String courseNameData = (String) requestData.get("courseName");
        String courseDescriptionData = (String) requestData.get("description");
        int coursePriceData = Integer.parseInt((String) requestData.get("price"));
        //Create course
        int newCourseId = courseService.addCourse(chapterData, categoryIds, courseNameData, courseDescriptionData, coursePriceData);

        // Create response object
        Map<String, Object> response = new HashMap<>();
        response.put("courseId", newCourseId);
        response.put("message", "Course created successfully");

        return ResponseEntity.ok(response);
    }
}
