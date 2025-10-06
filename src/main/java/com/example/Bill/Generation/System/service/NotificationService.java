package com.example.Bill.Generation.System.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${twilio.whatsapp.number}")
    private String twilioWhatsAppNumber;

    private String formatNumber(String modNo) {
        // If user entered without +91, prepend country code
        if (!modNo.startsWith("+")) {
            return "+91" + modNo;
        }
        return modNo;
    }

    public void sendSms(String modNo, String message) {
        Twilio.init(accountSid, authToken);
        String formattedNumber = formatNumber(modNo);

        Message sms = Message.creator(
                new com.twilio.type.PhoneNumber(formattedNumber),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                message
        ).create();

        System.out.println("✅ SMS Sent to " + formattedNumber + " | SID: " + sms.getSid());
    }

    public void sendWhatsApp(String modNo, String message) {
        Twilio.init(accountSid, authToken);
        String formattedNumber = formatNumber(modNo);

        Message waMsg = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + formattedNumber),
                new com.twilio.type.PhoneNumber(twilioWhatsAppNumber),
                message
        ).create();

        System.out.println("✅ WhatsApp Message Sent to " + formattedNumber + " | SID: " + waMsg.getSid());
    }
}
