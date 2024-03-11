package com.swd391.backend.utils;

import com.swd391.backend.entity.Rate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RateEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public RateEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRateChangedEvent(Rate rate, String action) {
        eventPublisher.publishEvent(new RateChangedEvent(this, rate, action));
    }
}
