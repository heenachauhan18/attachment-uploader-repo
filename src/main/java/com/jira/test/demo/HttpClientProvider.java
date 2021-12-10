package com.jira.test.demo;

import okhttp3.OkHttpClient;

import java.time.Duration;

public class HttpClientProvider {
    private final OkHttpClient httpClient;

    public HttpClientProvider() {
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMillis(5000000))
                .readTimeout(Duration.ofMillis(5000000))
                .writeTimeout(Duration.ofMillis(5000000))
                .build();
    }

    public OkHttpClient httpClient() {
        return httpClient;
    }
}
