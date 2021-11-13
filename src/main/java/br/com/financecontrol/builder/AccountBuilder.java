package br.com.financecontrol.builder;

import br.com.financecontrol.entity.Account;

public class AccountBuilder implements Builder {

  private Long id;
  private String uuid;

  public AccountBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public AccountBuilder uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  @Override
  public Account build() {
    return new Account(id, uuid);
  }
}