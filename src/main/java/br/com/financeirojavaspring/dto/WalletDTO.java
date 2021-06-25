package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.enums.TypeWallet;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class WalletDTO {

  private String uuid;
  private String title;
  private TypeWallet typeWallet;
}
