package com.swd391.backend.service.Interface;

public interface IEmailService {
    public void sendEmail(String from, String to, String subject, String body);
}
