package br.com.financeirojavaspring.dto;

import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class WalletSummaryDTO {

  private BigDecimal totalDebtor;
  private BigDecimal totalCreditor;
  private BigDecimal percentageCommitted;
  private BigDecimal percentagePaid;
}
