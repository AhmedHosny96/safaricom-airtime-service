package com.sahay.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {

    private String templateId;
    private String reference;
    private String toAccount;
    private String amount;
    private String msisdn;
    private String balance;
    private String transactionCost;
}
