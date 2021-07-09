package br.com.financeirojavaspring.builder.dto;

import br.com.financeirojavaspring.builder.Builder;
import br.com.financeirojavaspring.dto.PaymentDTO;

public class PaymentDTOBuilder implements Builder {

  private String uuidDebtor;
  private String uuidCreditor;

  public PaymentDTOBuilder uuidDebtor(String uuidDebtor) {
    this.uuidDebtor = uuidDebtor;
    return this;
  }

  public PaymentDTOBuilder uuidCreditor(String uuidCreditor) {
    this.uuidCreditor = uuidCreditor;
    return this;
  }

  @Override
  public PaymentDTO build() {
    return new PaymentDTO(uuidDebtor, uuidCreditor);
  }
}