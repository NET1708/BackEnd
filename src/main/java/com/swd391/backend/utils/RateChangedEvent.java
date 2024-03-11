package com.swd391.backend.utils;

import com.swd391.backend.entity.Rate;
import org.springframework.context.ApplicationEvent;

public class RateChangedEvent extends ApplicationEvent {
    private final Rate rate;
    private final String action;

    public RateChangedEvent(Object source, Rate rate, String action) {
        super(source);
        this.rate = rate;
        this.action = action;
    }

    // Getters
    public Rate getRate() {
        return rate;
    }

    public String getAction() {
        return action;
    }
}
