package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.enums.TypeWallet;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class WalletDTO implements Serializable {

  private String uuid;
  @NotNull
  @NotBlank
  private String title;
  @NotNull
  private TypeWallet typeWallet;

  private Integer dayWallet;

  private BigDecimal total;
}
