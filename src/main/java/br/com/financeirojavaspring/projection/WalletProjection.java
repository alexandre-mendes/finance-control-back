package br.com.financeirojavaspring.projection;

import br.com.financeirojavaspring.enums.TypeWallet;
import java.math.BigDecimal;

public interface WalletProjection {

  String getUuid();
  String getTitle();
  TypeWallet getTypeWallet();
  Integer getDayWallet();
  BigDecimal getValue();
  BigDecimal getValuePaid();
}
