package com.plantationhub.wesesta.authentication.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@Slf4j
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    @Value(("${twilio.messaging.service.sid}"))
    private String messagingSid;

    @PostConstruct
    public void initializeTwilio(){
        Twilio.init(accountSid, authToken);
        log.info("Twilio API initialized successfully on {}", accountSid);
    }

}
