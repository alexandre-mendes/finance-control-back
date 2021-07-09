package br.com.financeirojavaspring.builder.entity;

import br.com.financeirojavaspring.builder.Builder;
import br.com.financeirojavaspring.model.Account;
import br.com.financeirojavaspring.model.User;

public class UserBuilder implements Builder {

  private String username;
  private Long id;
  private String uuid;
  private String passwd;
  private String name;
  private Account account;

  public UserBuilder username(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public UserBuilder uuid(String uuid) {
    this.uuid = uuid;
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

  public UserBuilder account(Account account) {
    this.account = account;
    return this;
  }

  @Override
  public User build() {
    return new User(username);
  }
}