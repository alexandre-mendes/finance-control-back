package br.com.financecontrol.builder;

import br.com.financecontrol.entity.Invitation;
import br.com.financecontrol.entity.User;

public class InvitationBuilder implements Builder {

  private String id;
  private User userInvited;

  public InvitationBuilder id(String id) {
    this.id = id;
    return this;
  }

  public InvitationBuilder userInvited(User userInvited) {
    this.userInvited = userInvited;
    return this;
  }

  public Invitation build() {
    return new Invitation(id, userInvited);
  }
}