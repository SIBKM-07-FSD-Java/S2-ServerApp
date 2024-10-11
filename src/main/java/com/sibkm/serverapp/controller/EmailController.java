package com.sibkm.serverapp.controller;

import com.sibkm.serverapp.model.request.EmailRequest;
import com.sibkm.serverapp.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/email")
public class EmailController {

  private EmailService emailService;

  @PostMapping("/simple")
  public EmailRequest sendSimpleMessage(
    @RequestBody EmailRequest emailRequest
  ) {
    return emailService.sendSimpleMessage(emailRequest);
  }

  @PostMapping("/attach")
  public EmailRequest sendMessageWithAttachment(
    @RequestBody EmailRequest emailRequest
  ) {
    return emailService.sendMessageWithAttachment(emailRequest);
  }
}
