package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.PaymentDTOBuilder;
import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class PaymentDTO implements Serializable {
  private String uuidRecordDebtor;
  private String uuidWalletCreditor;

  public static PaymentDTOBuilder builder() {
    return new PaymentDTOBuilder();
  }
}
