package br.com.financecontrol.dto;

import br.com.financecontrol.enums.TypeWallet;
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

  private static final long serialVersionUID = 639051193088548873L;

  private String uuid;
  @NotNull
  @NotBlank
  private String title;
  @NotNull
  private TypeWallet typeWallet;

  private Integer dayWallet;

  private BigDecimal value;

  private BigDecimal valuePaid;
}
