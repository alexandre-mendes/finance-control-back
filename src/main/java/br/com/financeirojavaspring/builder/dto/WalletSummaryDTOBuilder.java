package br.com.financeirojavaspring.builder.dto;

import br.com.financeirojavaspring.dto.WalletSummaryDTO;
import java.math.BigDecimal;

public class WalletSummaryDTOBuilder {

  private BigDecimal totalDebtor;
  private BigDecimal totalCreditor;
  private BigDecimal percentageCommitted;
  private BigDecimal percentagePaid;

  public WalletSummaryDTOBuilder totalDebtor(BigDecimal totalDebtor) {
    this.totalDebtor = totalDebtor;
    return this;
  }

  public WalletSummaryDTOBuilder totalCreditor(BigDecimal totalCreditor) {
    this.totalCreditor = totalCreditor;
    return this;
  }

  public WalletSummaryDTOBuilder percentageCommitted(BigDecimal percentageCommitted) {
    this.percentageCommitted = percentageCommitted;
    return this;
  }

  public WalletSummaryDTOBuilder percentagePaid(BigDecimal percentagePaid) {
    this.percentagePaid = percentagePaid;
    return this;
  }

  public WalletSummaryDTO build() {
    return new WalletSummaryDTO(totalDebtor, totalCreditor, percentageCommitted, percentagePaid);
  }
}