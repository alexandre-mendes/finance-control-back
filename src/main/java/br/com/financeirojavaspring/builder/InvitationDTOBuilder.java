package br.com.financeirojavaspring.builder;

import br.com.financeirojavaspring.dto.InvitationDTO;

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