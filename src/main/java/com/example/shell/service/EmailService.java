package com.example.shell.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(String to, String subject,String txt);
}
