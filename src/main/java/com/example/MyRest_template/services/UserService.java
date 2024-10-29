package com.example.MyRest_template.services;

import com.example.MyRest_template.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private String sessionId;

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(
                BASE_URL, User[].class);
        this.sessionId = extractSessionId(responseEntity.getHeaders());
        return Arrays.asList(responseEntity.getBody());
    }

    private String extractSessionId(HttpHeaders headers) {
        List<String> cookies = headers.get(HttpHeaders.SET_COOKIE);
        if (cookies != null && !cookies.isEmpty()) {
            return cookies.get(0).split(";")[0];
        }
        return null;
    }

    public String saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                BASE_URL, request, String.class);
        return responseEntity.getBody(); // Возвращаем код
    }

    public String updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                BASE_URL, HttpMethod.PUT, request, String.class);
        return responseEntity.getBody(); // Возвращаем код
    }

    public String deleteUser(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                BASE_URL + "/" + userId, HttpMethod.DELETE, request, String.class);
        return responseEntity.getBody(); // Возвращаем код
    }
}