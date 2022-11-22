package com.bootcamp.melifrescos.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SendEmailServiceTest {
    @InjectMocks
    private SendEmailService sendEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void sendEmailTo_returnVoid_whenParamsSuccess() {
        Mockito.doNothing().when(javaMailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
        sendEmailService.sendEmailTo("", "", "");
        verifyNoMoreInteractions(javaMailSender);
    }
}
