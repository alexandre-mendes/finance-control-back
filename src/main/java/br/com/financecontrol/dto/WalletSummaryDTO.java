package br.com.financecontrol.dto;

import java.io.Serializable;
import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
public class WalletSummaryDTO implements Serializable {

  private static final long serialVersionUID = -2260833294875353894L;

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
