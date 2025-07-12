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

@CrossOrigin(origins = {"http://localhost:3000", "https://mms-frontend.netlify.app"})
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
            } 
            
            
            else if ("4".equals(digits)) {
                Dial dial = new Dial.Builder()
                        .number(new Number.Builder("+447388617329").build())
                        .build();

                twiml = new VoiceResponse.Builder()
                        .say(new Say.Builder("Connecting you now.I am gonna tell you a jocke untill mihretab answer  Please wait. Ethiopian Jokes\n" + //
                                                                "The New Shoes\n" + //
                                                                "A man buys a brand new pair of shoes. He walks out of the shop and immediately trips and falls. He looks at his shoes and says, 'See? I told you not to rush!'\n" + //
                                                                "\n" + //
                                                                "The Lazy Farmer\n" + //
                                                                "A farmer is incredibly lazy. One day, his neighbor asks him, 'Why don't you ever plant teff [a common Ethiopian grain]?' The farmer replies, 'Because it's too much work. You have to plant it, harvest it, thresh it, grind it, and then bake it into injera! I'll just wait for the bread to grow on trees.'\n" + //
                                                                "\n" + //
                                                                "The Speedy Driver\n" + //
                                                                "A taxi driver is going incredibly fast. His passenger is scared and says, 'Driver, please slow down! I'm not in a hurry to get to the next world!' The driver replies, 'Don't worry, we're not going to the next world. We're just going to the next traffic light!'\n" + //
                                                                "\n" + //
                                                                "The Student and the Exam\n" + //
                                                                "A student is taking an exam. He looks at the first question, then the second, then the third, and he doesn't know any of the answers. He raises his hand and asks the teacher, 'Excuse me, teacher, but is this exam supposed to be a secret?' The teacher asks, 'What do you mean?' The student replies, 'Because I don't know any of the answers!'\n" + //
                                                                "\n" + //
                                                                "The Doctor's Advice\n" + //
                                                                "A man goes to the doctor and says, 'Doctor, I can't sleep. I toss and turn all night.' The doctor thinks for a moment and says, 'Hmm, have you tried counting sheep?' The man sighs and says, 'Yes, but by the time I get to 100, I start thinking about all the things I need to do tomorrow, and then I'm wide awake again!'").build())
                        .dial(dial)
                        .build();
            }
            
            
            
            else {
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
