package br.com.financecontrol.builder;

import br.com.financecontrol.entity.Invitation;
import br.com.financecontrol.entity.User;

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