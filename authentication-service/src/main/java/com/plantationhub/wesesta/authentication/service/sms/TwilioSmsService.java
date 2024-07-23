package com.plantationhub.wesesta.authentication.service.sms;

import com.plantationhub.wesesta.authentication.exception.InvalidPhoneNumberException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class TwilioSmsService implements SmsService{
    @Value("${twilio.messaging.service.sid}")
    private String sender;

    private static final String NIGERIAN_PHONE_NUMBER_REGEX = "^(070|080|081|090|091)\\d{8}$|^234(70|80|81|90|91)\\d{8}$|^\\+234(70|80|81|90|91)\\d{8}$";
    private static final Pattern PATTERN = Pattern.compile(NIGERIAN_PHONE_NUMBER_REGEX);

    public void sendTokenSms(int phoneToken, String phoneNumber) {
        if (isValidPhone(phoneNumber)) {
            PhoneNumber to;
            if (phoneNumber.startsWith("0")) {
                to = new PhoneNumber("+234" + phoneNumber.substring(1));
            } else if (phoneNumber.startsWith("234")) {
                to = new PhoneNumber("+" + phoneNumber);
            } else if (phoneNumber.startsWith("+234")) {
                to = new PhoneNumber(phoneNumber);
            } else {
                throw new InvalidPhoneNumberException("Invalid phone number format: " + phoneNumber);
            }

            //PhoneNumber from = new PhoneNumber(senderPhoneNumber); use the messagingSid if the phone number isn't working correctly
            Message.creator(to, sender, "Your verification token is " + phoneToken).create();
            log.info("SMS has been sent to {}", phoneNumber);
        } else {
            throw new InvalidPhoneNumberException("Invalid phone number: " + phoneNumber);
        }
    }

    private boolean isValidPhone(String phoneNumber) {
        return PATTERN.matcher(phoneNumber).matches();
    }
}
