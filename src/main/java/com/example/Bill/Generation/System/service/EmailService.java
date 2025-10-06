package com.example.Bill.Generation.System.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${admin.email}")
    private String adminEmail;

    public void sendProductReport(String filePath,boolean thresholdAlert) throws MessagingException {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message,true);

        helper.setTo(adminEmail);
        helper.setSubject("\uD83D\uDCE6 Product Stock Report");

        String body;

        if (thresholdAlert) {
            body = "Dear Admin,\n\nPlease find the attached **complete product stock report**.\n\n⚠ ALERT: One or more products have reached the stock threshold!\n\nRegards,\nBilling System";
        } else {
            body = "Dear Admin,\n\nPlease find the attached **complete product stock report**.\n\nNo stock alerts.\n\nRegards,\nBilling System";
        }

        helper.setText(body);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        helper.addAttachment(file.getFilename(),file);

        mailSender.send(message);

        System.out.println(" ✅ Product Report sent to admin");

    }
}
