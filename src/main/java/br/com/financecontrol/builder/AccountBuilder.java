package br.com.financecontrol.builder;

import br.com.financecontrol.entity.Account;

public class AccountBuilder implements Builder {

  private String id;

  public AccountBuilder id(String id) {
    this.id = id;
    return this;
  }

  @Override
  public Account build() {
    return new Account(id);
  }
}