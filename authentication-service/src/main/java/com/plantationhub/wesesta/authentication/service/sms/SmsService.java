package com.plantationhub.wesesta.authentication.service.sms;

public interface SmsService {
    void sendTokenSms(int token, String phoneNumber);
}
