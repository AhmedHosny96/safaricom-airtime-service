package com.sahay.airtime;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/safari-airtime")
public class AirtimeController {

    private final AirtimeService airtimeService;

    public AirtimeController(AirtimeService airtimeService) {
        this.airtimeService = airtimeService;
    }

    // balance

    @GetMapping(value = "/balance")
    public ResponseEntity<?> getAirtimeBalance() throws JsonProcessingException {
        JSONObject airtimeBalance = airtimeService.getAirtimeBalance();

        //[{"code":"101","balance":"999535414.21 ETB","shortname":"eTopUP"}]
        JSONObject products = airtimeBalance.getJSONArray("products").getJSONObject(0);


        var airtimeBalanceResponse = new AirtimeBalanceResponse(
                "000",
                "successful",
                products.getString("code"),
                products.getString("shortname"),
                products.getString("balance")
        );
        return new ResponseEntity<>(airtimeBalanceResponse, HttpStatus.OK);
    }

    // recharge airtime

    @PostMapping("/recharge")
    public ResponseEntity<?> recharge(@RequestBody RechargeRequest rechargeRequest) {
        JSONObject rechargeResponse = airtimeService.rechargeAirtime(rechargeRequest);

        if (!rechargeResponse.getString("code").equals("1000")) {

            var errorResponse = new AirtimeCustomResponse("999", rechargeResponse.getString("message"));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
//
        var customResponse = new AirtimeCustomResponse(
                "000",
                rechargeResponse.getString("message")
        );
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }


}
