package com.sahay.airtime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "SafaricomAirtime")
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
