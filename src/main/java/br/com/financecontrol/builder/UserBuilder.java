package br.com.financecontrol.builder;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.User;

public class UserBuilder implements Builder {

  private String id;
  private String username;
  private String passwd;
  private String name;
  private String activationCode;
  private boolean active;
  private Account account;

  public UserBuilder username(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder id(String id) {
    this.id = id;
    return this;
  }

  public UserBuilder passwd(String passwd) {
    this.passwd = passwd;
    return this;
  }

  public UserBuilder name(String name) {
    this.name = name;
    return this;
  }

  public UserBuilder activationCode(String activationCode) {
    this.activationCode = activationCode;
    return this;
  }

  public UserBuilder active(boolean active) {
    this.active = active;
    return this;
  }

  public UserBuilder account(Account account) {
    this.account = account;
    return this;
  }

  @Override
  public User build() {
    return new User(id, username, passwd, name, activationCode, active, account);
  }
}