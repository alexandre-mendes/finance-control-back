package br.com.financecontrol.builder;

import br.com.financecontrol.dto.UserDTO;

public class UserDTOBuilder implements Builder {
  private String id;
  private String username;
  private String passwd;
  private String name;

  public UserDTOBuilder id(String id) {
    this.id = id;
    return this;
  }

  public UserDTOBuilder username(String username) {
    this.username = username;
    return this;
  }

  public UserDTOBuilder passwd(String passwd) {
    this.passwd = passwd;
    return this;
  }

  public UserDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public UserDTO build() {
    return new UserDTO(id, username, passwd, name);
  }
}