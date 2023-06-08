package com.sahay.airtime;

public record AirtimeBalanceResponse(
        String response,
        String responseDescription,
        String productCode,
        String productName,
        String balance
) {

}
