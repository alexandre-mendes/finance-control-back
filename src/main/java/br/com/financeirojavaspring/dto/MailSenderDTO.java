package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.MailSenderDTOBuilder;
import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class MailSenderDTO implements Serializable {
  private String emailDestiny;
  private String subject;
  private String message;

  public static MailSenderDTOBuilder builder() {
    return new MailSenderDTOBuilder();
  }
}
