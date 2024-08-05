package com.sahay.airtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "SafaricomAirtime")
public class Airtime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String phoneNumber;
    private Double amount;
    private String reference;
    private String safariReference;
    private String response;
    private LocalDateTime processedDate;
    private Boolean status;
    private String requestPayload;
    private String responsePayload;

}
