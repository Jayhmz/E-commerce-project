package com.plantationhub.email.service;

import jakarta.mail.MessagingException;

public interface SendMail {
    void sendMail(String email, String username) throws MessagingException;
}
