package com.sahay.airtime;

import java.time.LocalDate;


public record PaymentRequest
        (
        AirtimeRequest airtimeRequest,
        String customerPhoneNumber,
        String customerReferenceType,
        String customerName,
        LocalDate date,
        PaymentDetails paymentDetail
        ) {
    public record PaymentDetails
            (
            String transactionId,
            String amount
    ) {

    }

}
