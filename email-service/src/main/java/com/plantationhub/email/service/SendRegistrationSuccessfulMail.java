package com.plantationhub.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

@Service
public class SendRegistrationSuccessfulMail implements SendMail{

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ISpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public void sendMail(String email, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariable("token", token);

        String htmlContent = thymeleafTemplateEngine.process("successful-onboarding.html", context);
        helper.setSubject("Confirm Registration");
        helper.setText(htmlContent, true);
        helper.setTo(email);
        mailSender.send(message);
    }

}
