package com.java.user.management.service;

import com.java.user.management.constants.UserConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class ExternalService {

    private RestTemplate restTemplate;

    @Value("${api.backed.url}")
    private String backendUrl;

    public ExternalService(RestTemplate restTemplate){
        this.restTemplate =restTemplate;
    }

    @CircuitBreaker(name = UserConstants.SERVICE_A, fallbackMethod = "serviceAFallback")
    @Retry(name = UserConstants.SERVICE_A)
    @RateLimiter(name = UserConstants.SERVICE_A)
    public ResponseEntity<String> callExternalService(){
        String response = restTemplate.getForObject(backendUrl, String.class);
        log.info("able to hit the endpoint , response we got was: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
