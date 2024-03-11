package com.swd391.backend.service;

import com.swd391.backend.dao.CourseRepository;
import com.swd391.backend.dao.RateRepository;
import com.swd391.backend.dao.UserRepository;
import com.swd391.backend.entity.Rate;
import com.swd391.backend.service.Interface.IRateService;
import com.swd391.backend.utils.RateEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService implements IRateService {
    private final RateRepository rateRepository;
    private final RateEventPublisher eventPublisher;
    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private final UserRepository userRepository;

    public RateService(RateRepository rateRepository, RateEventPublisher eventPublisher, CourseRepository courseRepository, UserRepository userRepository) {
        this.rateRepository = rateRepository;
        this.eventPublisher = eventPublisher;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Rate createRate(int course_id, int user_id, Rate rate) {
        rate.setCourse(courseRepository.findById(course_id).
                orElseThrow(() -> new RuntimeException("Course not found with id " + course_id)));
        rate.setUser(userRepository.findById(user_id).
                orElseThrow(() -> new RuntimeException("User not found with id " + user_id)));
        Rate savedRate = rateRepository.save(rate);
        eventPublisher.publishRateChangedEvent(savedRate, "SAVE");
        return savedRate;
    }
}
