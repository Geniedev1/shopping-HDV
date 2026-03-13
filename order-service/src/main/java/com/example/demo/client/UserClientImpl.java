package com.example.demo.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserLookupResult;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
public class UserClientImpl implements UserClient {

    private final RestTemplate restTemplate;

    public UserClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "getUserByIdFallback")
    public UserLookupResult getUserById(Long userId) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String token = attributes.getRequest().getHeader("Authorization");
            if (token != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                // Assuming User Service runs on localhost:8080 as per previous context
                // Adjust URL if it's a different microservice
                String userServiceUrl = "http://auth-service:8081/api/users/" + userId;

                ResponseEntity<UserDTO> response = restTemplate.exchange(
                        userServiceUrl,
                        HttpMethod.GET,
                        entity,
                        UserDTO.class);
                return new UserLookupResult(response.getBody(), false, "REAL", "Success");
            }
        }
        return new UserLookupResult(null, false, "NO_TOKEN", "No token provided");
    }

    public UserLookupResult getUserByIdFallback(Long userId, Throwable throwable) {
        System.out.println("DEBUG: Fallback triggered for userId " + userId + ". Reason: " + throwable.getMessage());
        UserDTO fallbackUser = new UserDTO();
        fallbackUser.setId(userId);
        fallbackUser.setEmail("fallback-user-" + userId + "@demo.local");
        fallbackUser.setAddress("FALLBACK_ADDRESS_DOWNSTREAM_UNAVAILABLE");

        String status = "FALLBACK_FAILURE";
        if (throwable instanceof java.util.concurrent.TimeoutException ||
            throwable instanceof org.springframework.web.client.ResourceAccessException) {
            status = "FALLBACK_TIMEOUT";
        } else if (throwable instanceof io.github.resilience4j.circuitbreaker.CallNotPermittedException) {
            status = "FALLBACK_CIRCUIT_OPEN";
        }

        return new UserLookupResult(fallbackUser, true, status, throwable.getMessage());
    }
}
