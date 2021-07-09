package br.com.financeirojavaspring.dto;

import java.io.Serializable;
import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class TransferDTO implements Serializable {
  private String uuidOrigin;
  private String uuidDestiny;
  private BigDecimal valueTransfer;
}
