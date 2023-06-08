package com.sahay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@SpringBootApplication
public class SafaricomAirtimeServiceApplication {

//    private static final String PAYMENT_URL_1 = "https://app.ethiomobilemoney.et:2121/ammapi/payment/service-openup/toTradeWebPay";

    private static final String PAYMENT_URL = "http://196.188.120.3:11443/ammapi/payment/service-openup/toTradeMobielPay ";
    public final static String publickey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi/6iMQ78D6hlR4QH1WTGOiFU6R34xwMNj+sgHhlaKo2JdXn4CkteOAdAK6jhuRRsymeVlXcKN1gql5C9h24pAXaFFS6bTzlNIqQjAqYfta2PaDWh7hgqb/RPE57laV9CVvOmzKVhPd0JBSoG8Xj70C6Z0PyGRytY89L2ARqN5YaKE/vm3p89o+Op6otfVibWa3eR7XoZu4bmd487RYmHD9Pcrz7kR8Rq2G3zn0btf2Fj7wBlOaJBX8zZzUmQnQzB6eM8UDCeqLV4k3MUOEQNqL1tJDWOglhmsktWfgqMv+Tri7lpYITSBaW4zKa3DO9BDmZGFuKqIvzUqJSzGKrwwwIDAQAB";

    public static void main(String[] args) throws Exception {

        String ussdJson = "{\"appid\":\"21a5837f40124697853115db69064f55\",\"nonce\":\"06636e2195cb47bfa2a16a2d2423efc2\",\"notifyUrl\":\"http://www.google.com/notifyUrl\",\"outTradeNo\":\"202305101683715710703\",\"receiveName\":\"HUDHUD\",\"returnUrl\":\"http://www.google.com/returnUrl\",\"shortCode\":\"220401\",\"subject\":\"Test\",\"timeoutExpress\":\"30\",\"timestamp\":\"1683715710703\",\"totalAmount\":\"10\"}";

        SpringApplication.run(SafaricomAirtimeServiceApplication.class, args);

//        String stringA = generateStringA("805ed5e221254a2394acd4857df15812");
//
//        log.info("STRINGA : {}", stringA);
//
//        String sign = generateHash(stringA);
//        log.info("STRING B : {}", sign);

//        String stringB = generateSign(stringA);
//        log.info("STRING B : {}", stringB);

//        String encryptRSA = encryptRSA(ussdJson, publickey);
//        log.info("ENCRYPTED PAYLOAD : {}", encryptRSA);
//        String encryptRSA = encryptRSA(ussdJson, publickey);
//        log.info("ENCRYPTED PAYLOAD : {}", encryptRSA);
//        String decryptedData = decryptRSA(encryptRSA, publickey);
//        log.info("DECRYPTED RESULT : {}", decryptedData);

    }

//    @PostMapping("/makePayment")
//    public String makePayment(@RequestBody PaymentRequest paymentRequest) throws Exception {
////        String encryptedUSSD = encryptRSA(ussd,publickey);
//        Map<String, String> requestMessage = new HashMap<>();
//        requestMessage.put("appid", paymentRequest.appId());
//        requestMessage.put("sign", paymentRequest.sign());
//        requestMessage.put("ussd", paymentRequest.ussd());
////        System.out.println("\n\nNew Request: " + requestMessage + "\n\n");
//        log.info("NEW REQUEST : {}", requestMessage);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setContentLength(requestMessage.toString().getBytes().length);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.postForEntity(PAYMENT_URL, requestMessage, String.class);
//
//        if (response.getStatusCodeValue() != HttpURLConnection.HTTP_OK) {
//            return "Error: " + response.getStatusCode();
//        }
//
//        String result = response.getBody();
//        System.out.println("\nResult: " + result);
//        var paymentResponse = new PaymentResponse();
//        paymentResponse.parseFromJson(result);
//        System.out.println("\nNEW TOPAYURL: " + paymentResponse.getData().getToPayUrl());
//        return paymentResponse.getData().getToPayUrl();
//    }
//    // step : 1 generate string a
//    public static String generateStringA(String appKey) {
//        String outTradeNo = UUID.randomUUID().toString();
//        Map<String, String> data = new TreeMap<String, String>();
//        data.put("outTradeNo", outTradeNo);
//        data.put("subject", "Goods Name");
//        data.put("totalAmount", "10");
//        data.put("shortCode", "220401");
//        data.put("notifyUrl", "http://localhost/notifyUrl");
//        data.put("returnUrl", "http://localhost/returnUrl");
//        data.put("receiveName", "HUDHUD");
//        data.put("appid", "21a5837f40124697853115db69064f55");
//        data.put("timeoutExpress", "30");
//        data.put("nonce", UUID.randomUUID().toString());
//        data.put("timestamp", String.valueOf(Instant.now().getEpochSecond()));
//        data.put("appKey", appKey);
//        String stringA = "";
//        for (Map.Entry<String, String> entry : data.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if (stringA.equals("")) {
//                stringA = key + "=" + value;
//            } else {
//                stringA += "&" + key + "=" + value;
//            }
//        }
//        return "String A : \n" + stringA + "\n\n";
//    }
//    // step : 2 generate string B , SHA of string A
//
//    public static String generateHash(String stringA) throws NoSuchAlgorithmException {
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] hash = digest.digest(stringA.getBytes(StandardCharsets.UTF_8));
//        String stringB = bytesToHex(hash);
//        return stringB;
}

//    private static String bytesToHex(byte[] bytes) {
//        StringBuilder result = new StringBuilder();
//        for (byte b : bytes) {
//            result.append(String.format("%02x", b));
//        }
//        return result.toString();
//    }
//
//    public static String generateSign(String stringA) throws Exception {
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] hash = digest.digest(stringA.getBytes(StandardCharsets.UTF_8));
//        String sign = btyes(hash).toUpperCase();
//        return "Sign : \n" + sign + "\n\n";
//    }
//
//    private static String btyes(byte[] bytes) {
//        StringBuilder result = new StringBuilder();
//        for (byte b : bytes) {
//            result.append(String.format("%02x", b));
//        }
//        return result.toString();
//    }

// step 3 : encryption
//    public static String encryptRSA(String data, String publicKeyString) throws Exception {
//        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyString);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PublicKey publicKey = keyFactory.generatePublic(keySpec);
//
//        StringBuilder crypto = new StringBuilder();
//        byte[] chunk;
//        byte[] cryptoItem;
//
//        for (int i = 0; i < data.length(); i += 117) {
//            chunk = Arrays.copyOfRange(data.getBytes(StandardCharsets.UTF_8), i, Math.min(i + 117, data.length()));
//            cryptoItem = encryptChunkRSA(chunk, publicKey);
//            if (cryptoItem == null) {
//                throw new Exception("Encryption failed");
//            }
//            crypto.append(new String(cryptoItem, StandardCharsets.UTF_8));
//        }
//        return Base64.encodeBase64String(crypto.toString().getBytes(StandardCharsets.UTF_8));
//    }
//
//    private static byte[] encryptChunkRSA(byte[] chunk, PublicKey publicKey) {
//        try {
//            javax.crypto.Cipher rsa = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            rsa.init(javax.crypto.Cipher.ENCRYPT_MODE, publicKey);
//            return rsa.doFinal(chunk);
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public static ResponseEntity<String> httpPostJson(String url, String jsonStr) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        headers.setContentLength(jsonStr.getBytes(StandardCharsets.UTF_8).length);
//
//        HttpEntity<String> entity = new HttpEntity<>(jsonStr, headers);
//        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//
//        return restTemplate.postForEntity(url, entity, String.class);
//    }

//    // step 4 : decryption
//    public static String decryptRSA(String source, String key) throws Exception {
//        byte[] decodedKey = Base64.decodeBase64URLSafe(key);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
//        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
//
//        byte[] encryptedData = Base64.decodeBase64URLSafe(source);
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.DECRYPT_MODE, publicKey);
//
//        byte[] decrypted = new byte[0];
//        byte[] buffer = new byte[256];
//        for (int i = 0; i < encryptedData.length; i += 256) {
//            int length = Math.min(encryptedData.length - i, 256);
//            buffer = Arrays.copyOfRange(encryptedData, i, i + length);
//
//            byte[] partial = cipher.doFinal(buffer);
//            decrypted = Arrays.copyOf(decrypted, decrypted.length + partial.length);
//            System.arraycopy(partial, 0, decrypted, decrypted.length - partial.length, partial.length);
//        }
//        return new String(decrypted, "UTF-8");
//    }

