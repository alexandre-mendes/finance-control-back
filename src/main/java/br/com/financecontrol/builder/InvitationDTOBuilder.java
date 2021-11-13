package br.com.financecontrol.builder;

import br.com.financecontrol.dto.InvitationDTO;

public class InvitationDTOBuilder implements Builder {

  private String uuid;

  public InvitationDTOBuilder uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  @Override
  public InvitationDTO build() {
    return new InvitationDTO(uuid);
  }
}