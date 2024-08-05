package com.sahay.config;


import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Util {

    public String generateUniqueId() {
        Random random = new Random();
        int randomNumber = random.nextInt(900_000) + 100_000;
        return "Rays-" + randomNumber;
    }

    public Optional<String> extractRefNumber(String message) {
        String pattern = "Ref number (R\\d+\\.\\d+\\.\\d+)";
        Pattern refPattern = Pattern.compile(pattern);
        Matcher matcher = refPattern.matcher(message);

        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }

        return Optional.empty(); // Reference number not found
    }
}
