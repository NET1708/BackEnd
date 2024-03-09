package com.swd391.backend.service;

import com.swd391.backend.service.Interface.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Override
    public void sendEmail(String from, String to, String subject, String body) {
        // Send email
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        // thực hiện hành động gửi email
        emailSender.send(message);
    }
}
