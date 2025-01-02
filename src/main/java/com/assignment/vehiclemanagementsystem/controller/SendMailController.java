package com.assignment.vehiclemanagementsystem.controller;

import com.assignment.vehiclemanagementsystem.payload.respone.ResponseData;
import com.assignment.vehiclemanagementsystem.payload.respone.ResponseError;
import com.assignment.vehiclemanagementsystem.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class SendMailController {

    private final MailService mailService;

    @PostMapping("/send-email")
    public ResponseData<?> sendEmail(@RequestParam String recipients, @RequestParam String subject,
                                         @RequestParam String content, @RequestParam(required = false) MultipartFile[] files) {
        log.info("Request send-email");
        try {
            return new ResponseData(ACCEPTED.value(), mailService.sendEmail(recipients, subject, content, files));
        } catch (UnsupportedEncodingException | MessagingException e) {
            log.error("Sending email was failure, message={}", e.getMessage(), e);
            return new ResponseError(BAD_REQUEST.value(), "Sending email was failure");
        }
    }
}
