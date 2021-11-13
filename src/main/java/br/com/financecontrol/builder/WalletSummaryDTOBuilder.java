package br.com.financecontrol.builder;

import br.com.financecontrol.dto.WalletSummaryDTO;
import java.math.BigDecimal;

public class WalletSummaryDTOBuilder {

  private BigDecimal debitBalance;
  private BigDecimal totalCreditor;
  private BigDecimal totalDebtor;
  private BigDecimal totalPaid;
  private BigDecimal percentageCommitted;
  private BigDecimal percentagePaid;

  public WalletSummaryDTOBuilder debitBalance(BigDecimal debitBalance) {
    this.debitBalance = debitBalance;
    return this;
  }

  public WalletSummaryDTOBuilder totalCreditor(BigDecimal totalCreditor) {
    this.totalCreditor = totalCreditor;
    return this;
  }

  public WalletSummaryDTOBuilder totalDebtor(BigDecimal totalDebtor) {
    this.totalDebtor = totalDebtor;
    return this;
  }

  public WalletSummaryDTOBuilder totalPaid(BigDecimal totalPaid) {
    this.totalPaid = totalPaid;
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
    return new WalletSummaryDTO(debitBalance, totalCreditor, totalDebtor, totalPaid, percentageCommitted, percentagePaid);
  }
}