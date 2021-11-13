package br.com.financecontrol.service;

import br.com.financecontrol.dto.MailSenderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SenderMailService {
  private final JavaMailSender mailSender;

  @Autowired
  public SenderMailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void submit(final MailSenderDTO dto) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(dto.getEmailDestiny());
    email.setSubject(dto.getSubject());
    email.setText(dto.getMessage());
    mailSender.send(email);
  }
}