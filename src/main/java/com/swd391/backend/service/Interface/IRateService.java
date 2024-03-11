package com.swd391.backend.service.Interface;

import com.swd391.backend.entity.Rate;

public interface IRateService {
    Rate createRate(int course_id, int user_id, Rate rate);
}
