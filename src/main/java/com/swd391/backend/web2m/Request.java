package com.swd391.backend.web2m;

import lombok.Data;

import java.util.List;
@Data
public class Request {
    private String status;
    private List<String> data;
}
