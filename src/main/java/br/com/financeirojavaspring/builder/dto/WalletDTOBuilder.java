package br.com.financeirojavaspring.builder.dto;

import br.com.financeirojavaspring.builder.Builder;
import br.com.financeirojavaspring.dto.WalletDTO;
import br.com.financeirojavaspring.enums.TypeWallet;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import org.hibernate.persister.entity.Loadable;

public class WalletDTOBuilder implements Builder {

  private String uuid;
  private String title;
  private TypeWallet typeWallet;
  private Integer dayWallet;

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

  public WalletDTOBuilder dateWallet(Integer dayWallet) {
    this.dayWallet = dayWallet;
    return this;
  }

  @Override
  public WalletDTO build() {
    return new WalletDTO(uuid, title, typeWallet, dayWallet);
  }
}