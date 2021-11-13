package br.com.financecontrol.builder;

import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.enums.TypeWallet;
import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.entity.Wallet;
import java.math.BigDecimal;
import java.util.List;

public class WalletBuilder implements Builder {
  private Long id;
  private String uuid;
  private String title;
  private TypeWallet typeWallet;
  private List<RecordDebtor> recordDebtor;
  private List<RecordCreditor> recordCreditor;
  private Integer dayWallet;
  private Account account;
  private BigDecimal value;
  private BigDecimal valuePaid;

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

  public WalletBuilder recordCreditor(List<RecordCreditor> recordCreditor) {
    this.recordCreditor = recordCreditor;
    return this;
  }

  public WalletBuilder account(Account account) {
    this.account = account;
    return this;
  }

  public WalletBuilder dateWallet(Integer dayWallet) {
    this.dayWallet = dayWallet;
    return this;
  }

  public WalletBuilder value(BigDecimal value) {
    this.value = value;
    return this;
  }

  public WalletBuilder valuePaid(BigDecimal valuePaid) {
    this.valuePaid = valuePaid;
    return this;
  }

  @Override
  public Wallet build() {
    return new Wallet(id, uuid, title, typeWallet, recordDebtor, recordCreditor, dayWallet, account, value, valuePaid);
  }
}