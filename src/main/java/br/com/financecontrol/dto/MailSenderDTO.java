package br.com.financecontrol.dto;

import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
public class MailSenderDTO implements Serializable {

  private static final long serialVersionUID = -6922906849046479398L;

  private String emailDestiny;
  private String subject;
  private String message;

  public static MailSenderDTOBuilder builder() {
    return new MailSenderDTOBuilder();
  }
}
