package br.com.financecontrol.builder;

import br.com.financecontrol.dto.UserDTO;

public class UserDTOBuilder implements Builder {
  private String uuid;
  private String username;
  private String passwd;
  private String name;

  public UserDTOBuilder uuid(String uuid) {
    this.uuid = uuid;
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
    return new UserDTO(uuid, username, passwd, name);
  }
}