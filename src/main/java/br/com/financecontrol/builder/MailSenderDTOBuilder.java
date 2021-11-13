package br.com.financecontrol.builder;

import br.com.financecontrol.dto.MailSenderDTO;

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