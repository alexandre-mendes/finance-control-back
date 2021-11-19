package br.com.financecontrol.dto;

import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
public class PaymentDTO implements Serializable {

  private static final long serialVersionUID = 5035205799947430904L;

  private String recordDebtorId;
  private String walletCreditorId;

  public static PaymentDTOBuilder builder() {
    return new PaymentDTOBuilder();
  }
}
