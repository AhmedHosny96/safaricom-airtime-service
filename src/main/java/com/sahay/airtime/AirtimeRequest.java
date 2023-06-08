package com.sahay.airtime;
import java.util.List;



public record AirtimeRequest(
        String type,
        List<IdRequest> id,
        String password
) {
}
