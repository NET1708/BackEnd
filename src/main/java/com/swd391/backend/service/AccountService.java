package com.swd391.backend.service;

import com.swd391.backend.dao.UserRepository;
import com.swd391.backend.entity.ErrorMessage;
import com.swd391.backend.entity.User;
import com.swd391.backend.service.Interface.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.naming.Context;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public ResponseEntity<?> register(User user) {

        // Check if username or email is already taken
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Username đã tồn tại!"));
        }

        // Check if email or email is already taken
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Email này đã được sử dụng!"));
        }

        // Encode the password
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // Set and send activation code
        user.setActivationCode(generateActivationCode());
        user.setActive(false);

        // Save the user to the database
        userRepository.save(user);

        // Send the activation email
        sendEmailActivation(user.getEmail(), user.getFullName(), user.getActivationCode());

        return ResponseEntity.ok().body("Đăng ký thành công!");
    }

    private String generateActivationCode() {
        // Generate a random activation code
        return UUID.randomUUID().toString();
    }

    private void sendEmailActivation(String email, String username, String activationCode) {
        String subject = "Xác nhận tài khoản";
        //Create context for thymeleaf
        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
        String url = "https://ani-testlab.edu.vn/activate/" + email + "/" + activationCode;
        context.setVariable("name", username);
        context.setVariable("activationCode", url);
        String htmlBody = templateEngine.process("/EmailTemplate/ActivationEmail.html", context);
        emailService.sendEmail("mail@ani-testlab.edu.vn", email, subject, htmlBody);
    }

    public ResponseEntity<?> activateAccount(String email, String activationCode) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Tài khoản không tồn tại!"));
        }

        if (user.isActive()) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Tài khoản đã được kích hoạt!"));
        }

        if (!user.getActivationCode().equals(activationCode)) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Mã kích hoạt không đúng!"));
        }

        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.ok().body("Kích hoạt tài khoản thành công!");
    }
}
