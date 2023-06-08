package com.sahay.config;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.RequestBuilder;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.util.concurrent.atomic.AtomicReference;

import static org.asynchttpclient.Dsl.asyncHttpClient;

@Service
@Slf4j
public class AsyncHttpConfig {


    private final AsyncHttpClientConfig clientConfig;

    public AsyncHttpConfig() {
        io.netty.handler.ssl.SslContext sc = null;
        try {
            sc = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } catch (SSLException e) {
            e.printStackTrace();
        }
        clientConfig = new DefaultAsyncHttpClientConfig.Builder()
                .setCompressionEnforced(true)
                .setMaxConnections(100)
                .setPooledConnectionIdleTimeout(20000)
                .setRequestTimeout(20000)
                .setMaxConnectionsPerHost(5000)
                .setSslContext(sc)
                .build();
    }
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
    public JSONObject sendRequest(RequestBuilder request) {
        AtomicReference<JSONObject> responseBody = new AtomicReference<>(new JSONObject());
        try (AsyncHttpClient client = asyncHttpClient(clientConfig)) {
            client
                    .executeRequest(request)
                    .toCompletableFuture()
                    .thenApply(response -> {
                        log.info(this.getClass().getName(), response.getResponseBody());
                        JSONObject responseObject = new JSONObject();
                        try {
                            log.info(this.getClass().getName(), String.format("Received [HttpStatus = %d, Body = %s, URL = %s] processing halted!", response.getStatusCode(), response.getResponseBody(), responseObject));
                            responseBody.set(new JSONObject(response.getResponseBody()));
                        } catch (Exception e) {
                            log.error(this.getClass().getName(), String.format("JSON Processing failed, [Message= %s, URL= %s]", e.getMessage(), ""));
                        }
                        return responseObject;
                    }).thenAccept(u -> log.info(this.getClass().getName(), u.toString())).join();
        } catch (Exception e) {
            log.error(this.getClass().getName(), String.format("Micro-Service Sending Failed,[Type= %s,Message= %s]", "", e.getMessage()));
        }
        return responseBody.get();
    }
}
