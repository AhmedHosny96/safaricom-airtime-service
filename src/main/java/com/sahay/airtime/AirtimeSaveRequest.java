package com.sahay.airtime;

import java.time.LocalDateTime;


public record AirtimeSaveRequest(
        String phoneNumber,
        Double amount,
        String reference,
        String safariReference,
        String response,
        Boolean status,
        LocalDateTime processedDate,
        String requestPayload,
        String responsePayload
) {
}
