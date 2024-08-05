package com.sahay.airtime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirtimeBalanceResponse {

    private String response;
    private String responseDescription;
    private String productCode;
    private String productName;
    private String balance;

}
