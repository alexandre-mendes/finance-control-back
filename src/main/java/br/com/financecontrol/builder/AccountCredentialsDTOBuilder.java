package br.com.financecontrol.builder;

import br.com.financecontrol.dto.AccountCredentialsDTO;

public class AccountCredentialsDTOBuilder implements Builder {

  private String username;
  private String password;

  public AccountCredentialsDTOBuilder username(String username) {
    this.username = username;
    return this;
  }

  public AccountCredentialsDTOBuilder password(String password) {
    this.password = password;
    return this;
  }

  @Override
  public AccountCredentialsDTO build() {
    return new AccountCredentialsDTO(username, password);
  }
}