package com.pkalinov.autopartsmgmtserver.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

public class MailService {

    private final JavaMailSender javaMailSender;
    private final String springMailUsername;
    private final String autoPartsMgmtMailReceiver;

    public MailService(JavaMailSender javaMailSender, String springMailUsername, String autoPartsMgmtMailReceiver) {
        this.javaMailSender = javaMailSender;
        this.springMailUsername = springMailUsername;
        this.autoPartsMgmtMailReceiver = autoPartsMgmtMailReceiver;
    }

    @Async
    public void sendSimpleMessage(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("AutoParts Manager <" + springMailUsername + ">");
        message.setTo(autoPartsMgmtMailReceiver);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}