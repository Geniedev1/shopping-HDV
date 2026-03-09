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

@Component
public class UserClientImpl implements UserClient {

    private final RestTemplate restTemplate;

    public UserClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String token = attributes.getRequest().getHeader("Authorization");
            if (token != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                // Assuming User Service runs on localhost:8080 as per previous context
                // Adjust URL if it's a different microservice
                String userServiceUrl = "http://localhost:8080/api/users/" + userId;
                
                try {
                    ResponseEntity<UserDTO> response = restTemplate.exchange(
                            userServiceUrl,
                            HttpMethod.GET,
                            entity,
                            UserDTO.class
                    );
                    return response.getBody();
                } catch (Exception e) {
                   // Handle exception (log it, return null, or throw custom exception)
                   e.printStackTrace();
                   return null;
                }
            }
        }
        return null;
    }
}
