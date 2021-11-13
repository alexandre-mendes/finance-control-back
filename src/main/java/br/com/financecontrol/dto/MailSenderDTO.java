package br.com.financecontrol.dto;

import br.com.financecontrol.builder.MailSenderDTOBuilder;
import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class MailSenderDTO implements Serializable {

  private static final long serialVersionUID = -6922906849046479398L;

  private String emailDestiny;
  private String subject;
  private String message;

  public static MailSenderDTOBuilder builder() {
    return new MailSenderDTOBuilder();
  }
}
