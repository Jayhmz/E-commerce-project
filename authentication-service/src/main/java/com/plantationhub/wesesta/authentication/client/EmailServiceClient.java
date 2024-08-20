package com.plantationhub.wesesta.authentication.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "EMAIL-SERVICE", url = "http://localhost:8080/api/v1/mail")
public interface EmailServiceClient {

    @PostMapping("/send-login-notification")
    ResponseEntity<String> sendLoginAlertMail(@RequestParam String email, @RequestParam String username);
}
