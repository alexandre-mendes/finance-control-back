package br.com.financeirojavaspring.builder;

import br.com.financeirojavaspring.dto.MailSenderDTO;

public class MailSenderDTOBuilder implements Builder {

  private String emailDestiny;
  private String subject;
  private String message;

  public MailSenderDTOBuilder emailDestiny(String emailDestiny) {
    this.emailDestiny = emailDestiny;
    return this;
  }

  public MailSenderDTOBuilder subject(String subject) {
    this.subject = subject;
    return this;
  }

  public MailSenderDTOBuilder message(String message) {
    this.message = message;
    return this;
  }

  @Override
  public MailSenderDTO build() {
    return new MailSenderDTO(emailDestiny, subject, message);
  }
}