package br.com.financecontrol.dto;

import java.io.Serializable;
import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class TransferDTO implements Serializable {

  private static final long serialVersionUID = -3236063067362694926L;

  private String originId;
  private String destinyId;
  private BigDecimal valueTransfer;
}
