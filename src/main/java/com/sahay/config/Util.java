package com.sahay.config;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Util {

    public String generateUniqueId(){
        var random = new Random();
        int randomNumber = random.nextInt(900_000) + 100_000;
        return "RAYS-" + randomNumber;
    }
}
