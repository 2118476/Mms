package com.mms.sms.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class CallService {

    private final String sid = System.getenv("TWILIO_ACCOUNT_SID");
    private final String token = System.getenv("TWILIO_AUTH_TOKEN");
    private final String from = System.getenv("TWILIO_PHONE_NUMBER");

 String BASE_CALLBACK_URL = "https://mms-backend-5erf.onrender.com/api/outbound-call";

    public String makeCall(String to) {
        Twilio.init(sid, token);

        try {
            String callbackWithNumber = BASE_CALLBACK_URL + "?to=" + URLEncoder.encode(to, StandardCharsets.UTF_8.toString());

            Call call = Call.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(from),
                    new URI(callbackWithNumber)).create();

            return "📞 Call started! SID: " + call.getSid();
        } catch (Exception e) {
            return "❌ Call failed: " + e.getMessage();
        }
    }
}
