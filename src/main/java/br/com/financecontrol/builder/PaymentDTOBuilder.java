package br.com.financecontrol.builder;

import br.com.financecontrol.dto.PaymentDTO;

public class PaymentDTOBuilder implements Builder {

  private String idDebtor;
  private String idCreditor;

  public PaymentDTOBuilder idDebtor(String idDebtor) {
    this.idDebtor = idDebtor;
    return this;
  }

  public PaymentDTOBuilder idCreditor(String idCreditor) {
    this.idCreditor = idCreditor;
    return this;
  }

  @Override
  public PaymentDTO build() {
    return new PaymentDTO(idDebtor, idCreditor);
  }
}