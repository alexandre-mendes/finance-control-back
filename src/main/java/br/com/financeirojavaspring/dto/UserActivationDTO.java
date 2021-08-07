package br.com.financeirojavaspring.dto;

import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class UserActivationDTO implements Serializable {

  private static final long serialVersionUID = -1280445739701560965L;

  private String email;
  private String activationCode;
}
