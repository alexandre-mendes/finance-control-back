package br.com.financeirojavaspring.builder.dto;

import br.com.financeirojavaspring.builder.Builder;
import br.com.financeirojavaspring.dto.WalletDTO;
import br.com.financeirojavaspring.enums.TypeWallet;
import java.lang.annotation.Annotation;

public class WalletDTOBuilder implements Builder {

  private String uuid;
  private String title;
  private TypeWallet typeWallet;

  public WalletDTOBuilder uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public WalletDTOBuilder title(String title) {
    this.title = title;
    return this;
  }

  public WalletDTOBuilder typeWallet(TypeWallet typeWallet) {
    this.typeWallet = typeWallet;
    return this;
  }

  @Override
  public WalletDTO build() {
    return new WalletDTO(uuid, title, typeWallet);
  }
}