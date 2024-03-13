package com.swd391.backend.web2m;

import com.swd391.backend.entity.Transaction;
import lombok.Data;

import java.util.List;
@Data
public class Request {
    private boolean status;
    private List<Transaction> data;
}
