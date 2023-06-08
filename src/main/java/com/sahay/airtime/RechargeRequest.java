package com.sahay.airtime;

public record RechargeRequest(
        String transactionId,
        String toAccount,
        String fromAccount,
        String amount
) {
}
