package com.swd391.backend.utils;

import com.swd391.backend.service.ExternalApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//@Component
public class FetchAPI {
    private final ExternalApiService externalApiService;

    public FetchAPI(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

//    @Scheduled(fixedRate = 10000)
    public void fetch() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDate = currentDate.format(formatter);
        String endDate = currentDate.plusYears(1).format(formatter);
        externalApiService.login("0356855236", "Anhvinh123!");
        //sleep 5s
        try {
            Thread.sleep(5000);
            externalApiService.getListTransaction("00001643062", startDate, endDate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
