package br.com.financecontrol.dto;

import br.com.financecontrol.builder.PaymentDTOBuilder;
import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class PaymentDTO implements Serializable {

  private static final long serialVersionUID = 5035205799947430904L;

  private String uuidRecordDebtor;
  private String uuidWalletCreditor;

  public static PaymentDTOBuilder builder() {
    return new PaymentDTOBuilder();
  }
}
