package com.swd391.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swd391.backend.dao.TransactionRepository;
import com.swd391.backend.entity.Transaction;
import com.swd391.backend.service.TransactionService;
import com.swd391.backend.web2m.Request;
import com.swd391.backend.web2m.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
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
    private TransactionService transactionService;
    @PostMapping
    public ResponseEntity<String> processWebhook(@RequestHeader(value = "Authorization", defaultValue = "Bearer 2a82b9fac355fa8c192b28e7f47fbdb8") String accessToken,
                                                 @RequestHeader(value = "Content-Type", defaultValue = "application/json") String Content,
                                                 @RequestBody Request request) throws JsonProcessingException {
        return transactionService.processWebhook(accessToken, Content, request);
    }
}