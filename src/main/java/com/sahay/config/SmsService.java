package com.sahay.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.RequestBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {

    @Value("${rays.sms-endpoint}")
    private String SMS_URL;

    @Value(value = "${rays.sp-caller}")
    private String SP_URL;

    private final AsyncHttpConfig http;

    public void sendSms(SmsRequest smsRequest) {
        try {

            JSONObject createSmsRequest = createSmsRequest(smsRequest);
            String[] keywords = createSmsRequest.keySet().stream()
                    .toArray(String[]::new);

            String smsBody = generateMessage(createSmsRequest, keywords);

            log.info("send message : " + smsRequest);
            JSONObject smsPayload = new JSONObject();
            smsPayload.put("TransactionReqType", "SAVE-SMS");
            smsPayload.put("TRANS_REF", smsRequest.getReference());
            smsPayload.put("PHONENUMBER", smsRequest.getMsisdn());
            smsPayload.put("MESSAGE", smsBody);

            RequestBuilder builder = new RequestBuilder("POST");
            builder.addHeader("Content-Type", "application/json")
                    .setBody(smsPayload.toString())
                    .setUrl(SMS_URL)
                    .build();
            http.sendRequest(builder);

        } catch (Exception ex) {
            log.warn("Error Generating SMS : {} " + ex.getMessage());
        }
    }

    public JSONObject createSmsRequest(SmsRequest request) {
        JSONObject smsRequest = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mma");
        smsRequest.put("TemplateId", request.getTemplateId());
        smsRequest.put("refno", request.getReference());
        smsRequest.put("date", LocalDate.now().toString());
        smsRequest.put("time", dateFormat.format(new Date()));
        smsRequest.put("amount", request.getAmount());
        smsRequest.put("account", request.getToAccount());
        smsRequest.put("balance", request.getBalance());
        smsRequest.put("cost", request.getTransactionCost());
        return smsRequest;
    }


    public String generateMessage(JSONObject data, String[] words) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("TransactionReqType", "SMS-TEMPLATE");
        jsonRequest.put("TemplateId", data.getString("TemplateId"));
        jsonRequest.put("TemplatePhone", data.getString("account"));

        RequestBuilder smsPayload = new RequestBuilder("POST");
        smsPayload.addHeader("Content-Type", "application/json")
                .setBody(jsonRequest.toString())
                .setUrl(SP_URL)
                .build();

        JSONObject smsResponse = http.sendRequest(smsPayload);

        log.info("SMS RESPONSE : {}", smsResponse);

        AtomicReference<String> message = new AtomicReference<>(smsResponse.getString("Template"));
        try {
            for (String word : words)
                try {
                    message.set(message.get().replace("[" + word + "?]", data.getString(word)));
                } catch (Exception ex) {
                    log.warn("Error Mapping SMS :{}}" + ex.getMessage());
                }
        } catch (Exception ex) {
            log.warn("Error Generating SMS : {} " + ex.getMessage());
        }
        return message.get();
    }
}
