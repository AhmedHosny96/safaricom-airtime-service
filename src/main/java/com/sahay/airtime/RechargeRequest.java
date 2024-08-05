package com.sahay.airtime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargeRequest {

    private String transactionId;
    private String toAccount;
    private String fromAccount;
    private String amount;
}

