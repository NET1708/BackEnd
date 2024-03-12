package com.swd391.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swd391.backend.dao.TransactionRepository;
import com.swd391.backend.entity.Transaction;
import com.swd391.backend.web2m.Request;
import com.swd391.backend.web2m.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Webhook", description = "Webhook APIs")
@RequestMapping("/webhook")
@CrossOrigin(origins = "http://localhost:3000, https://api.ani-testlab.edu.vn")
public class WebhookController {
    @Autowired
    private TransactionRepository transactionRepository;
    @PostMapping
    public ResponseEntity<String> processWebhook(@RequestHeader("Authorization") String accessToken, @RequestBody Request request) throws JsonProcessingException {
        // Xác thực token
        if (!isValidToken(accessToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // Kiểm tra dữ liệu webhook
        if (request.getData() == null || request.getData().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid webhook data");
        }

        // Xử lý dữ liệu webhook
        List<Transaction> transactions;
        if (request.isStatus()) {
            transactions = request.getData();
        } else {
            return ResponseEntity.badRequest().body("Invalid webhook status");
        }

        // Lưu trữ các giao dịch
        saveTransactions(transactions);

        // Gửi phản hồi thành công
        Response response = new Response();
        response.setStatus(true);
        response.setMsg("Ok");
        return ResponseEntity.ok(new ObjectMapper().writeValueAsString(response));
    }

    private boolean isValidToken(String bearerToken) {
        // Lấy bearer token từ header
        String token = bearerToken.substring(7);
        // Kiểm tra xem bearerToken có khớp với accessToken
        return token.equals("2a82b9fac355fa8c192b28e7f47fbdb8");
    }

    private void saveTransactions(List<Transaction> transactions) {
        // Lưu trữ các giao dịch vào cơ sở dữ liệu
        transactionRepository.saveAll(transactions);
    }
}