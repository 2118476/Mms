package com.mms.sms.controller;

import com.mms.sms.model.SmsRequest;
import com.mms.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://gorgeous-cendol-eb18cc.netlify.app")

public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send-sms")
    public String sendSms(@RequestBody SmsRequest request) {
        return smsService.sendSms(request.getPhoneNumber(), request.getMessage());
    }
}
