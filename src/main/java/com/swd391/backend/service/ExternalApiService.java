package com.swd391.backend.service;

import com.swd391.backend.dao.TransactionInfosRepository;
import com.swd391.backend.entity.TpBankAPI;
import com.swd391.backend.utils.ListTransactionResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalApiService {
    private final RestTemplate restTemplate;
    private String accessToken;
    private int accessTokenExpirationTime;
    private final TransactionInfosRepository transactionInfosRepository;

    public ExternalApiService(RestTemplateBuilder restTemplateBuilder, TransactionInfosRepository transactionInfosRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.transactionInfosRepository = transactionInfosRepository;
    }

    public void login(String username, String password) {
        String url = "https://ebank.tpb.vn/gateway/api/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("Accept-Language", "en-US,en;q=0.9");
        headers.set("Device_id", "ZeSKwDK6yEHw4A8Jc7XAWhJnHlTopDXoCB3PBHMuqO1Ll");
        headers.set("Referer", "https://ebank.tpb.vn");
        headers.set("Sec-Ch-Ua", "\"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Microsoft Edge\";v=\"122\"");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/5...");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", username);
        body.add("password", password);
        body.add("step_2FA", "VERIFY");
        body.add("deviceId", "ZeSKwDK6yEHw4A8Jc7XAWhJnHlTopDXoCB3PBHMuqO1Ll");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        // Gửi request POST với headers và body
        TpBankAPI response = restTemplate.postForObject(url, entity, TpBankAPI.class);
        if (response != null) {
            accessToken = response.getAccess_token();
            accessTokenExpirationTime = response.getExpires_in();
        }
    }

    public void getListTransaction(String accountNo, String fromDate, String toDate) {
        String url = "https://ebank.tpb.vn/gateway/api/smart-search-presentation-service/v2/account-transactions/find";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Accept-Language", "en-US,en;q=0.9");
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Device_id", "ZeSKwDK6yEHw4A8Jc7XAWhJnHlTopDXoCB3PBHMuqO1Ll");
        headers.set("Referer", "https://ebank.tpb.vn");
        headers.set("Sec-Ch-Ua", "\"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Microsoft Edge\";v=\"122\"");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0");

        Map<String, Object> body = new HashMap<>();
        body.put("pageNumber", 1);
        body.put("pageSize", 400);
        body.put("accountNo", accountNo);
        body.put("currency", "VND");
        body.put("maxAcentrysrno", "");
        body.put("fromDate", fromDate);
        body.put("toDate", toDate);
        body.put("keyword", "");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Gửi request POST với headers và body
        ListTransactionResponse response = restTemplate.postForObject(url, entity, ListTransactionResponse.class);

        // Xử lý dữ liệu response
        if (response != null) {
            //Lọc dữ liệu nếu trùng thì không lưu vào database
            response.getTransactionInfos().forEach(transactionInfos -> {
                if (transactionInfosRepository.findById(transactionInfos.getId()).isEmpty()) {
                    transactionInfosRepository.save(transactionInfos);
                }
            });
        }
    }
}
