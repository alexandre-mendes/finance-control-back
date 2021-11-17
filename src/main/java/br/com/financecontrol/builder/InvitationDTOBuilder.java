package br.com.financecontrol.builder;

import br.com.financecontrol.dto.InvitationDTO;

public class InvitationDTOBuilder implements Builder {

  private String id;

  public InvitationDTOBuilder id(String id) {
    this.id = id;
    return this;
  }

  @Override
  public InvitationDTO build() {
    return new InvitationDTO(id);
  }
}