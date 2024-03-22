package com.swd391.backend.utils;

import com.swd391.backend.entity.TransactionInfos;
import lombok.Data;

import java.util.List;

@Data
public class ListTransactionResponse {
    private String totalRows;
    private String maxAcentrysmo;
    private List<TransactionInfos> transactionInfos;
}
