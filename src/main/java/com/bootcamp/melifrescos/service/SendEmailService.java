package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.ISendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService implements ISendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Método responsável por enviar o email para cliente
     * @param recipient
     * @param subject
     * @param text
     */
    @Override
    public void sendEmailTo(String recipient, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(text);
        javaMailSender.send(email);
    }
}
