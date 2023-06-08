package com.sahay.airtime;

import org.json.JSONObject;

import java.util.List;

public record AirtimeBalanceResponse(
        String response,
        String responseDescription,
        String productCode,
        String productName,
        String balance
) {


}
