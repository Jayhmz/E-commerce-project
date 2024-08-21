package com.plantationhub.email.controller;

import com.plantationhub.email.service.SendMail;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mail")
@Slf4j
public class AuthenticationMailController {

    private final SendMail sendMail;

    public AuthenticationMailController(@Qualifier("sendLoginSuccessfulMail") SendMail sendMail) {
        this.sendMail = sendMail;
    }

    @PostMapping("/send-login-notification")
    public ResponseEntity<?> sendLoginAlertMail(@RequestParam String email, @RequestParam String username) throws MessagingException {
        sendMail.sendMail(email, username);
        log.info("inside the send login notification controller");
        return new ResponseEntity<>("email sent successfully", HttpStatus.OK);
    }

    @GetMapping("/send-registration-mail")
    public ResponseEntity<?> sendRegistrationMail(String email, String username) throws MessagingException {
        sendMail.sendMail(email, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
