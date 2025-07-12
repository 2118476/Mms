package com.mms.sms.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account.sid}")
    private String sid;

    @Value("${twilio.auth.token}")
    private String token;

    @Value("${twilio.phone.number}")
    private String from;

    public String sendSms(String to, String body) {
        try {
            Twilio.init(sid, token);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(from),
                    body
            ).create();

            return "SMS sent! SID: " + message.getSid();
        } catch (Exception e) {
            return "Failed to send: " + e.getMessage();
        }
    }
}
