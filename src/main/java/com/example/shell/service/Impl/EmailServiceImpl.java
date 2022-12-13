package com.example.shell.service.Impl;

import com.example.shell.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    private final JavaMailSender emailSender;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);


    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(String to, String subject,String txt) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(txt);
        executorService.submit(() -> {
            emailSender.send(message);
        });
    }
}
