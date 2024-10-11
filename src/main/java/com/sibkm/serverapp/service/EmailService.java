package com.sibkm.serverapp.service;

import com.sibkm.serverapp.model.request.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService {

  private JavaMailSender javaMailSender;

  public EmailRequest sendSimpleMessage(EmailRequest emailRequest) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(emailRequest.getTo());
    message.setSubject(emailRequest.getSubject());
    message.setText(emailRequest.getBody());
    javaMailSender.send(message);

    return emailRequest;
  }

  public EmailRequest sendMessageWithAttachment(EmailRequest emailRequest) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(emailRequest.getTo());
      helper.setSubject(emailRequest.getSubject());
      helper.setText(emailRequest.getBody());

      FileSystemResource file = new FileSystemResource(
        new File(emailRequest.getAttachment())
      );

      helper.addAttachment(file.getFilename(), file);

      javaMailSender.send(message);
      log.info("Successfully send email...");
    } catch (Exception e) {
      System.out.println("Error " + e.getMessage());
      log.error("Failed to send email...");
    }
    return emailRequest;
  }
}
