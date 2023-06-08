package com.sahay.auth;

import com.sahay.config.AsyncHttpConfig;
import com.sahay.config.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.RequestBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    @Value("${rays.bank-name}")
    private String BANK_NAME;

    @Value("${safaricom.token-url}")
    private String AUTH_URL;

    @Value("${safaricom.identity-token}")
    private String IDENTITY_TOKEN;

    @Value("${safaricom.username}")
    private String USERNAME;
    @Value("${safaricom.password}")
    private String PASSWORD;

    private final AsyncHttpConfig http;

    private final Util util;

    public String getToken() {
        var authRequest = new JSONObject();
//        authRequest.put("username", "PRETUPS");
//        authRequest.put("password", "MRyhsEPWzO8jDqaE0EAKnw==");
        authRequest.put("username", USERNAME);
        authRequest.put("password", PASSWORD);

        var authBody = new RequestBuilder("POST");
        authBody
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .addHeader("x-source-system", BANK_NAME)
                .addHeader("x-source-identity-token", IDENTITY_TOKEN)
                .addHeader("x-correlation-conversationid", util.generateUniqueId())
                .setBody(authRequest.toString())
                .setUrl(AUTH_URL)
                .build();
        JSONObject authResponse = http.sendRequest(authBody);
        log.info("AUTH RESPONSE :{}", authResponse);
        return authResponse.getString("Token");
    }
}
