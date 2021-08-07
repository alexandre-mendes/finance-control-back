package br.com.financeirojavaspring.dto;

import java.io.Serializable;
import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class TransferDTO implements Serializable {

  private static final long serialVersionUID = -3236063067362694926L;

  private String uuidOrigin;
  private String uuidDestiny;
  private BigDecimal valueTransfer;
}
