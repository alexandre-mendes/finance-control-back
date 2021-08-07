package br.com.financeirojavaspring.builder;

import br.com.financeirojavaspring.entity.Invitation;
import br.com.financeirojavaspring.entity.User;

public class InvitationBuilder implements Builder {

  private Long id;
  private String uuid;
  private User userInvited;

  public InvitationBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public InvitationBuilder uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public InvitationBuilder userInvited(User userInvited) {
    this.userInvited = userInvited;
    return this;
  }

  public Invitation build() {
    return new Invitation(id, uuid, userInvited);
  }
}