package br.com.financeirojavaspring.dto;

import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class UserActivationDTO implements Serializable {
  private String email;
  private String activationCode;
}
