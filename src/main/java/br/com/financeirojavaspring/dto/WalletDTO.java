package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.enums.TypeWallet;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class WalletDTO {

  private String uuid;
  @NotNull
  @NotBlank
  private String title;
  @NotNull
  @NotBlank
  private TypeWallet typeWallet;
}
