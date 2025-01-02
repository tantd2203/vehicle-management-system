package com.assignment.vehiclemanagementsystem.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String emailFrom;


    public String sendEmail(String recipients, String subject, String content, MultipartFile[] files) throws UnsupportedEncodingException, MessagingException {
        log.info("Email is sending ...");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailFrom);

        if (recipients.contains(",")) { // send to multiple users
            helper.setTo(InternetAddress.parse(recipients));
        } else { // send to single user
            helper.setTo(recipients);
        }

        // Send attach files
        if (files != null) {
            for (MultipartFile file : files) {
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }
        }
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);

        log.info("Email has sent to successfully, recipients: {}", recipients);

        return "Sent";
    }


    @KafkaListener(topics = "register-success-topic", groupId = "register-success-group")
    private void sendEmailKafka(String message) throws UnsupportedEncodingException, MessagingException {
        log.info("Email is sending ...");

        log.info("Sending link to user, email={}", message);

        String[] arr = message.split(",");
        String emailTo = arr[0];
        String userId = arr[1];
        String content = arr[2];

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(emailFrom);

        if (emailTo.contains(",")) { // send to multiple users
            helper.setTo(InternetAddress.parse(emailTo));
        } else { // send to single user
            helper.setTo(emailTo);
        }
        helper.setSubject("register success");
        helper.setText(content, true);

        mailSender.send(mimeMessage);

        log.info("Email has sent to successfully, recipients: {}", emailTo);

    }


}
