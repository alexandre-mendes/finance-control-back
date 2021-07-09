package br.com.financeirojavaspring.builder.entity;

import br.com.financeirojavaspring.builder.Builder;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.model.Account;
import br.com.financeirojavaspring.model.RecordDebtor;
import br.com.financeirojavaspring.model.Wallet;
import java.util.List;

public class WalletBuilder implements Builder {
  private Long id;
  private String uuid;
  private String title;
  private TypeWallet typeWallet;
  private List<RecordDebtor> recordDebtor;
  private List<RecordDebtor> recordCreditor;
  private Account account;

  public WalletBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public WalletBuilder uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public WalletBuilder title(String title) {
    this.title = title;
    return this;
  }

  public WalletBuilder typeWallet(TypeWallet typeWallet) {
    this.typeWallet = typeWallet;
    return this;
  }

  public WalletBuilder recordDebtor(List<RecordDebtor> recordDebtor) {
    this.recordDebtor = recordDebtor;
    return this;
  }

  public WalletBuilder recordCreditor(List<RecordDebtor> recordCreditor) {
    this.recordCreditor = recordCreditor;
    return this;
  }

  public WalletBuilder account(Account account) {
    this.account = account;
    return this;
  }

  @Override
  public Wallet build() {
    return new Wallet(id, uuid, title, typeWallet, recordDebtor, recordCreditor, account);
  }
}