package com.bootcamp.melifrescos.interfaces;

public interface ISendEmailService {

    void sendEmailTo(String recipient, String subject, String text);
}
