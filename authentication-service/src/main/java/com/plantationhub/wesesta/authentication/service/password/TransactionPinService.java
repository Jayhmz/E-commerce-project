package com.plantationhub.wesesta.authentication.service.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TransactionPinService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TransactionPinService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String generatePin(){
        Random random = new Random();
        int pin = 100000 + random.nextInt(900000);
        return passwordEncoder.encode(String.valueOf(pin));
    }
}
