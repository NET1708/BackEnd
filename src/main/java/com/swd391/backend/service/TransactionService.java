package com.swd391.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swd391.backend.dao.TransactionRepository;
import com.swd391.backend.entity.Transaction;
import com.swd391.backend.service.Interface.ITransactionService;
import com.swd391.backend.web2m.Request;
import com.swd391.backend.web2m.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public ResponseEntity<String> processWebhook(String accessToken, String Content, Request request) {
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
            return ResponseEntity.badRequest().body("Invalid webhook data");
        }

        // Lưu trữ các giao dịch
        saveTransactions(transactions);

        // Gửi phản hồi thành công
        Response response = new Response();
        response.setStatus(true);
        response.setMsg("Ok");

        try {
            String jsonResponse = objectMapper.writeValueAsString(response);
            return ResponseEntity.ok(jsonResponse);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process response");
        }
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
