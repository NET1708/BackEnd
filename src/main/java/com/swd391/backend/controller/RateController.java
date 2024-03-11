package com.swd391.backend.controller;

import com.swd391.backend.entity.Rate;
import com.swd391.backend.service.RateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Rate", description = "Rate management APIs")
@RequestMapping("/rate")
@CrossOrigin(origins = "http://localhost:3000, https://api.ani-testlab.edu.vn")
public class RateController {
    @Autowired
    private RateService rateService;

    @PostMapping
    public void createRate(int course_id, int user_id, @RequestBody Rate rate) {
        rateService.createRate(course_id, user_id, rate);
    }
}
