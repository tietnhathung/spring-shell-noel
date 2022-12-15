package com.example.shell.service.Impl;

import com.example.shell.service.EmailService;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    private final JavaMailSender emailSender;
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

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
            try {
                emailSender.send(message);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });
    }

    @Override
    public void sendEmailHtml(String to, String subject, Map<String, String> gift) throws MessagingException, IOException {
        Resource resource = resourceLoader.getResource("classpath:noel.html");
        String htmlRaw = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline(htmlRaw);
        String text = template.apply(gift);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        emailSender.send(message);
        executorService.submit(() -> {
            try {
                emailSender.send(message);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });
    }
}
