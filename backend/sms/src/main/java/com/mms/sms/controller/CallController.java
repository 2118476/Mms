package com.mms.sms.controller;

import com.mms.sms.model.CallRequest;
import com.mms.sms.service.CallService;
import com.twilio.http.HttpMethod;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.*;
import com.twilio.twiml.voice.Number;
import com.twilio.twiml.voice.Record;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "https://gorgeous-cendol-eb18cc.netlify.app")

@RestController
@RequestMapping("/api")
public class CallController {

    @Autowired
    private CallService callService;

    @PostMapping("/make-call")
    public String makeCall(@RequestBody CallRequest request) {
        return callService.makeCall(request.getPhoneNumber());
    }

    @RequestMapping(value = "/voice", method = { RequestMethod.GET, RequestMethod.POST })
    public void handleIncomingCall(HttpServletResponse response) throws IOException {
        Gather gather = new Gather.Builder()
                .action("https://mms-backend-5erf.onrender.com/api/handle-key")
                .method(HttpMethod.POST)
                .numDigits(1)
                .say(new Say.Builder(
                        "Welcome to Mihretab business group. Press 1 to talk to customer support. Press 2 to leave a message. Press 3 to opt out.")
                        .voice(Say.Voice.ALICE)
                        .language(Say.Language.EN_GB)
                        .build())
                .build();

        VoiceResponse twiml = new VoiceResponse.Builder()
                .gather(gather)
                .say(new Say.Builder("We did not receive any input. Goodbye.").build())
                .build();

        response.setContentType("application/xml");
        response.getWriter().write(twiml.toXml());
    }

    @PostMapping("/handle-key")
    public void handleKeyPress(@RequestParam("Digits") String digits, HttpServletResponse response) throws IOException {
        VoiceResponse twiml;

        try {
            if ("1".equals(digits)) {
                Dial dial = new Dial.Builder()
                        .number(new Number.Builder("+4477388617329").build())
                        .build();

                twiml = new VoiceResponse.Builder()
                        .say(new Say.Builder("Connecting you now. Please wait.").build())
                        .dial(dial)
                        .build();

            } else if ("2".equals(digits)) {
                twiml = new VoiceResponse.Builder()
                        .say(new Say.Builder("Please leave your message after the beep. Press the hash key when done.").build())
                        .record(new Record.Builder().maxLength(60).finishOnKey("#").build())
                        .say(new Say.Builder("Thank you for your message. Goodbye.").build())
                        .build();
            } else if ("3".equals(digits)) {
                Dial dial = new Dial.Builder()
                        .number(new Number.Builder("+447982381476").build())
                        .build();

                twiml = new VoiceResponse.Builder()
                        .say(new Say.Builder("Connecting you now. Please wait.").build())
                        .dial(dial)
                        .build();
            } else {
                twiml = new VoiceResponse.Builder()
                        .say(new Say.Builder("Invalid input. Goodbye.").build())
                        .build();
            }

            response.setContentType("application/xml");
            response.getWriter().write(twiml.toXml());

        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><Say>An internal error occurred.</Say></Response>");
        }
    }

    @PostMapping("/outbound-call")
    public void handleOutboundCall(@RequestParam("to") String to, HttpServletResponse response) throws IOException {
        Dial dial = new Dial.Builder()
                .record(Dial.Record.RECORD_FROM_ANSWER)
                .number(new Number.Builder(to).build())
                .build();

        VoiceResponse twiml = new VoiceResponse.Builder()
                .say(new Say.Builder("Connecting you now, please wait...").build())
                .dial(dial)
                .build();

        response.setContentType("application/xml");
        response.getWriter().write(twiml.toXml());
    }
    @GetMapping("/health")
public String health() {
    return "OK";
}

}
