package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.WalletSummaryDTOBuilder;
import java.io.Serializable;
import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class WalletSummaryDTO implements Serializable {

  private BigDecimal debitBalance;
  private BigDecimal totalCreditor;
  private BigDecimal totalDebtor;
  private BigDecimal totalPaid;
  private BigDecimal percentageCommitted;
  private BigDecimal percentagePaid;

  public static WalletSummaryDTOBuilder builder() {
    return new WalletSummaryDTOBuilder();
  }
}
