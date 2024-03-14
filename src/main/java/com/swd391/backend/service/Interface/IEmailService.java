package com.swd391.backend.service.Interface;

public interface IEmailService {
    void sendEmail(String from, String to, String subject, String body);
}
