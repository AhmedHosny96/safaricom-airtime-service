package com.sahay.airtime;

import com.sahay.auth.AuthService;
import com.sahay.config.AsyncHttpConfig;
import com.sahay.config.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.RequestBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirtimeService {

    @Value("${safaricom.route-id}")
    private String ROUTE_ID;
    @Value("${rays.bank-name}")
    private String BANK_NAME;

    @Value("${safaricom.recharge-request-type}")
    private String RECHARGE_REQUEST_TYPE;

    @Value("${safaricom.balance-request-type}")
    private String BALANCE_REQUEST_TYPE;

    @Value("${safaricom.account-number}")
    private String SAFARICOM_ACCOUNT_NUMBER;

    @Value("${safaricom.url}")
    private String SAFARICOM_ENDPOINT;

    @Value("${rays.esb-url}")
    private String ESB_URL;

    @Value("${safaricom.schema-name}")
    private String SCHEMA_NAME;

    @Value("${safaricom.pin-key}")
    private String PIN_KEY;

    @Value("${safaricom.pin-value}")
    private String PIN_VALUE;

    @Value("${safaricom.customer-reference-type}")
    private String CUSTOMER_REFERECE_TYPE;
    @Value("${safaricom.request-password}")
    private String REQUEST_PASSWORD;

    private final AsyncHttpConfig http;

    private final AuthService authService;

    private final AirtimeRepository airtimeRepository;

    private final Util util;

    public JSONObject getAirtimeBalance() {

//        var firstId = new IdRequest(
//                SAFARICOM_ACCOUNT_NUMBER,
//                SCHEMA_NAME
//        );
//        var secondId = new IdRequest(PIN_KEY, PIN_VALUE);
//
//        var balanceQueryRequest = new AirtimeRequest(
//                BALANCE_REQUEST_TYPE,
//                Arrays.asList(firstId, secondId),
//                REQUEST_PASSWORD
//        );

        var id_1 = new QueryBalance.Id(SAFARICOM_ACCOUNT_NUMBER, SCHEMA_NAME);
        var id_2 = new QueryBalance.Id(PIN_KEY, PIN_VALUE);

        var balanceQueryRequest = new QueryBalance(BALANCE_REQUEST_TYPE, Arrays.asList(id_1, id_2), REQUEST_PASSWORD);

        log.info("AIRTIME BALANCE REQUEST : {}", balanceQueryRequest);
        // send http request

        var request = new JSONObject();
        request.put("type", BALANCE_REQUEST_TYPE);

        var idArray = new JSONArray();

        var firstId = new JSONObject();

        firstId.put("value", SAFARICOM_ACCOUNT_NUMBER);
        firstId.put("schemeName", SCHEMA_NAME);
        idArray.put(firstId);
        var secondId = new JSONObject();
        secondId.put("value", PIN_VALUE);
        secondId.put("schemeName", PIN_KEY);
        idArray.put(secondId);

        request.put("id", idArray);
        request.put("password", REQUEST_PASSWORD);

        log.info("AIRTIME BALANCE REQUEST : {}", request);

        String TOKEN = authService.getToken();

        var balanceRequest = new RequestBuilder("POST");
        balanceRequest
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", TOKEN))
                .addHeader("x-source-system", BANK_NAME)
                .addHeader("x-route-id", ROUTE_ID)
                .addHeader("x-correlation-id", util.generateUniqueId())
                .setUrl(SAFARICOM_ENDPOINT)
                .setBody(request.toString())
                .build();

        log.info("AIRTIME PAYLOAD : {}", balanceRequest);
        JSONObject airtimeBalanceResponse = http.sendRequest(balanceRequest);
        log.info("AIRTIME BALANCE RESPONSE : {}", airtimeBalanceResponse);
        return airtimeBalanceResponse;

    }


    public JSONObject rechargeAirtime(RechargeRequest request) {

        Optional<Airtime> airtimeByReference = airtimeRepository.findAirtimeByReference(request.transactionId());

        var customResponse = new JSONObject();

        if (airtimeByReference.isPresent()) {
            customResponse.put("response", "004");
            customResponse.put("responseDescription", "Duplicate airtime reference");

            log.info("CUSTOM RESPONSE : {}", customResponse);
            return customResponse;
        }

        String TOKEN = authService.getToken();

        var rechargeRequest = new JSONObject();

        rechargeRequest.put("type", RECHARGE_REQUEST_TYPE);
        var idArray = new JSONArray();
        var firstId = new JSONObject();
        firstId.put("value", SAFARICOM_ACCOUNT_NUMBER);
        firstId.put("schemeName", SCHEMA_NAME);
        idArray.put(firstId);
        var secondId = new JSONObject();
        secondId.put("value", PIN_VALUE);
        secondId.put("schemeName", PIN_KEY);
        idArray.put(secondId);

        rechargeRequest.put("id", idArray);
        rechargeRequest.put("password", REQUEST_PASSWORD);

        var payment = new JSONObject();
        payment.put("customerReference", request.toAccount());
        payment.put("customerReferenceType", CUSTOMER_REFERECE_TYPE);
        payment.put("date", LocalDate.now());

        var paymentDetails = new JSONObject();
        paymentDetails.put("transactionId", request.transactionId());
        paymentDetails.put("amountPaid", request.amount());

        payment.put("paymentDetails", paymentDetails);
        rechargeRequest.put("payment", payment);

        log.info("AIRTIME PURCHASE REQUEST : {}", rechargeRequest);

        var requestBody = new RequestBuilder("POST");
        requestBody
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", TOKEN))
                .addHeader("x-source-system", BANK_NAME)
                .addHeader("x-route-id", ROUTE_ID)
                .addHeader("x-correlation-id", util.generateUniqueId())
                .setUrl(SAFARICOM_ENDPOINT)
                .setBody(rechargeRequest.toString())
                .build();

        JSONObject airtimeRechargeResponse = http.sendRequest(requestBody);

        boolean status = true;
        String response = "000";

        log.info("AIRTIME PURCHASE RESPONSE : {}", airtimeRechargeResponse.toString());

        if (!airtimeRechargeResponse.getString("code").equals("1000")) {
            status = false;
            response = "999";
        } else {
            deductCustomer(request.toAccount(), request.fromAccount(), request.amount());
        }
        var airtimeSaveRequest = new AirtimeSaveRequest(
                request.toAccount(), Double.valueOf(request.amount()),
                request.transactionId(), airtimeRechargeResponse.getString("TransactionID"),
                response, status, LocalDateTime.now(), rechargeRequest.toString(), airtimeRechargeResponse.toString()
        );

        saveAirtime(airtimeSaveRequest);
        return airtimeRechargeResponse;
    }

    public void saveAirtime(AirtimeSaveRequest airtimeSaveRequest) {
        var airtime = new Airtime();
        airtime.setAmount(airtimeSaveRequest.amount());
        airtime.setPhoneNumber(airtimeSaveRequest.phoneNumber());
        airtime.setReference(airtimeSaveRequest.reference());
        airtime.setSafariReference(airtimeSaveRequest.safariReference());
        airtime.setProcessedDate(airtimeSaveRequest.processedDate());
        airtime.setResponse(airtimeSaveRequest.response());
        airtime.setRequestPayload(airtimeSaveRequest.requestPayload());
        airtime.setResponsePayload(airtimeSaveRequest.responsePayload());
        airtime.setStatus(airtimeSaveRequest.status());
        airtimeRepository.save(airtime);
    }

    public JSONObject deductCustomer(String toAccount, String fromAccount, String amount) {

        var deductionRequest = new JSONObject();
        deductionRequest.put("username", "channel");
        deductionRequest.put("password", "$_@C0NNEKT");
        deductionRequest.put("messageType", "1200");
        deductionRequest.put("serviceCode", "270");
        deductionRequest.put("transactionType", "ATTC");
        deductionRequest.put("msisdn", toAccount);
        deductionRequest.put("transactionId", util.generateUniqueId());
        deductionRequest.put("fromAccount", fromAccount);
        deductionRequest.put("toAccount", toAccount);
        deductionRequest.put("amount", amount);
        deductionRequest.put("prepaid", "1");
        deductionRequest.put("timestamp", Timestamp.from(Instant.now()));
        deductionRequest.put("channel", "AIRTIME_ADAPTOR");

        log.info("CUSTOMER DEDUCTION REQUEST : {}", deductionRequest);

        var deductionPayload = new RequestBuilder("POST");
        deductionPayload
                .setUrl(ESB_URL)
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .setBody(deductionRequest.toString())
                .build();

        JSONObject deductionResponse = http.sendRequest(deductionPayload);

        log.info("CUSTOMER DEDUCTION RESPONSE : {}", deductionResponse);

        return deductionResponse;
    }


}
