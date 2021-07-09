package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.dto.WalletDTOBuilder;
import br.com.financeirojavaspring.enums.TypeWallet;
import java.io.Serializable;
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
  @NotBlank
  private TypeWallet typeWallet;

  public static WalletDTOBuilder builder() {
    return new WalletDTOBuilder();
  }
}
