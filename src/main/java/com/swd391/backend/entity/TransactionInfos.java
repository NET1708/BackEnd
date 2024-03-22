package com.swd391.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction_infos")
public class TransactionInfos {
    @Id
    private String id;
    private String arrangementId;
    private String reference;
    private String description;
    private String category;
    private String bookingDate;
    private String valueDate;
    private String amount;
    private String currency;
    private String creditDebitIndicator;
    private String runningBalance;
    private String ofsAcctNo;
    private String ofsAcctName;
    private String creditorBankNameVn;
    private String creditorBankNameEn;
}
