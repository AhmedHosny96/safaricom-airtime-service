package com.sahay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PaymentResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("toPayUrl")
        private String toPayUrl;

        public String getToPayUrl() {
            return toPayUrl;
        }

        public void setToPayUrl(String toPayUrl) {
            this.toPayUrl = toPayUrl;
        }
    }

    public void parseFromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PaymentResponse paymentResponse = objectMapper.readValue(json, PaymentResponse.class);
            setData(paymentResponse.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
