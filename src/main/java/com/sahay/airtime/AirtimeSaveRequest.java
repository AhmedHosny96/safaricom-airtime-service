package com.sahay.airtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirtimeSaveRequest {

    private String phoneNumber;
    private Double amount;
    private String reference;
    private String safariReference;
    private String response;
    private Boolean status;
    private LocalDateTime processedDate;
    private String requestPayload;
    private String responsePayload;
}
