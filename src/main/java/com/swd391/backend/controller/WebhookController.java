package com.swd391.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.swd391.backend.dao.TransactionRepository;
import com.swd391.backend.entity.Transaction;
import com.swd391.backend.web2m.Request;
import com.swd391.backend.web2m.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import kotlin.jvm.internal.TypeReference;
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
    public ResponseEntity<String> processWebhook(@RequestHeader("Authorization") String bearerToken, @RequestBody Request request) {
        // Xác thực token
        if (!isValidToken(bearerToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // Kiểm tra dữ liệu webhook
        if (request.getData() == null || request.getData().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid webhook data");
        }

        // Xử lý dữ liệu webhook
        List<Transaction> transactions = request.getData().stream()
                .map(this::mapToTransaction)
                .collect(Collectors.toList());

        // Lưu trữ các giao dịch
        saveTransactions(transactions);

        // Gửi phản hồi thành công
        Response response = new Response();
        response.setStatus("true");
        response.setMsg("Ok");
        // Chuyển đổi đối tượng Response thành JSON
        String responseJson = new Gson().toJson(response);
        return ResponseEntity.ok(responseJson);
    }

    private boolean isValidToken(String bearerToken) {
        // Lấy bearer token từ header
        String token = bearerToken.substring(7);

        // Kiểm tra xem bearerToken có khớp với accessToken
        return token.equals("2a82b9fac355fa8c192b28e7f47fbdb8");
    }

    private Transaction mapToTransaction(String transactionData) {
        // Sử dụng chuỗi dữ liệu giao dịch để tạo một Map
        Map<String, String> transactionMap = Arrays.stream(transactionData.split(","))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(arr -> arr[0].trim(), arr -> arr[1].trim()));

        // Tạo một đối tượng Transaction mới từ Map
        Transaction transaction = new Transaction();
        transaction.setId(transactionMap.get("id"));
        transaction.setType(transactionMap.get("type"));
        transaction.setTransactionID(transactionMap.get("transactionID"));
        transaction.setAmount(transactionMap.get("amount"));
        transaction.setDescription(transactionMap.get("description"));
        transaction.setBank(transactionMap.get("bank"));

        return transaction;
    }

    private void saveTransactions(List<Transaction> transactions) {
        // Lưu trữ các giao dịch vào cơ sở dữ liệu
        transactionRepository.saveAll(transactions);
    }
}