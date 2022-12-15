package com.example.shell.service;

import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
    void sendEmail(String to, String subject,String txt);
    void sendEmailHtml(String to, String subject, Map<String, String> gifts) throws MessagingException, IOException;
}
