package com.swd391.backend.utils;

import com.swd391.backend.dao.CourseRepository;
import com.swd391.backend.entity.Course;
import com.swd391.backend.entity.Rate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RateChangeListener {
    private final CourseRepository courseRepository;

    public RateChangeListener(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @EventListener
    public void handleRateChangedEvent(RateChangedEvent event) {
        Rate rate = event.getRate();
        Course course = rate.getCourse();
        course.updateAverageRating(); // Gọi phương thức cập nhật averageRating
        courseRepository.save(course);
    }
}
