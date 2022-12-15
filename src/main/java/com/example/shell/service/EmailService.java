package com.example.shell.service;

import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;

public interface EmailService {
    void sendEmail(String to, String subject,String txt);
    public void sendEmailHtml(String to, String subject,String txt) throws MessagingException, IOException;
}
