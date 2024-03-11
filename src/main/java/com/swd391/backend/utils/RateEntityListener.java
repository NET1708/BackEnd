package com.swd391.backend.utils;

import com.swd391.backend.entity.Rate;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class RateEntityListener {

    private final RateEventPublisher eventPublisher;

    public RateEntityListener(RateEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @HandleAfterCreate
    public void handleRateCreated(Rate rate) {
        eventPublisher.publishRateChangedEvent(rate, "SAVE");
    }

    @HandleAfterDelete
    public void handleRateDeleted(Rate rate) {
        eventPublisher.publishRateChangedEvent(rate, "DELETE");
    }
}
