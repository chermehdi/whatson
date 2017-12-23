package com.mql.whatson.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mql.whatson.entity.Token;
import com.mql.whatson.entity.User;
import com.mql.whatson.entity.dao.TokenDao;
import com.mql.whatson.entity.dao.UserDao;

/**
 * @author Houda_Oul
 */
public class MailServiceImpl implements MailService {

    @Inject
    private UserDao userRepository;

    @Inject
    private TokenDao tokenRepository;

    private Session session;

    @Resource
    private ManagedExecutorService executorService;

    @PostConstruct
    private void init() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("houda.oulachguer@usmba.ac.ma", "houda1234"); // TODO: load from env
            }
        });
    }

    private Message createMessage(String email, String subject, String messageBody, Session session)
            throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("houda.oulachguer@usmba.ac.ma", "OULACHGUER Houda"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    @Override
    public void sendSimpleMail(String toEmail, String body) {

        try {
            Transport.send(createMessage("", "subject", "body", session));
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendHtmlMail(String toEmail, String template) {

        String tokenValue = UUID.randomUUID().toString();
        User user = null;
        user = userRepository.findByEmail(toEmail);
        if (user == null || user.isActive())
            return;

        Token t = new Token();
        t.setToken(tokenValue);
        user.setToken(t);
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("houda.oulachguer@usmba.ac.ma", "WhatsOnTeam"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toEmail));
            // TODO: should be fixed by @Mehdi
            message.setContent(String.format("click to verify email <a href='%s'>link</a>",
                    "http://localhost:8080/whatson/verify?token=" + tokenValue), "text/html");
            executorService.submit(() -> {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
