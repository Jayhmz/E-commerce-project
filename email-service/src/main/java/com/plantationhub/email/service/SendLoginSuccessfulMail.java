package com.plantationhub.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

@Service
@Slf4j
public class SendLoginSuccessfulMail implements SendMail{

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ISpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public void sendMail(String email, String username) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariable("user", username);

        String htmlContent = thymeleafTemplateEngine.process("login-alert.html", context);
        helper.setText(htmlContent, true);
        helper.setTo(email);
        //mailSender.send(message);
        log.info("The email has been successfully sent to {}", email);
    }

}
