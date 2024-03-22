package com.swd391.backend.entity;

import lombok.Data;

@Data
public class TpBankAPI {
    private String access_token;
    private String token_type;
    private int expires_in;
}
