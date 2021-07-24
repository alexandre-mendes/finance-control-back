package br.com.financeirojavaspring.projection;

import java.math.BigDecimal;

public interface VWWalletCreditorProjection {
  Long getIdWallet();
  String getUuid();
  String getTitle();
  String getTypeWallet();
  Long getIdAccount();
  Integer getDayWallet();
  BigDecimal getValue();
}
