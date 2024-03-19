package com.swd391.backend.service.Interface;

import com.swd391.backend.web2m.Request;
import org.springframework.http.ResponseEntity;

public interface ITransactionService {
    ResponseEntity<String> processWebhook(String accessToken, String Content, Request request);
}
