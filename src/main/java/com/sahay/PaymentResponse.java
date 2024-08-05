package com.sahay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Data data;

    public void parseFromJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject dataJson = jsonObject.getJSONObject("data");

        data = new Data();
        data.parseFromJson(dataJson);
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private String toPayUrl;

        public void parseFromJson(JSONObject dataJson) {
            toPayUrl = dataJson.getString("toPayUrl");
        }

        public String getToPayUrl() {
            return toPayUrl;
        }
    }
}
