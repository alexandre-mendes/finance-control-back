package br.com.financecontrol.builder;

import br.com.financecontrol.dto.RecordCreditorDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecordCreditorDTOBuilder implements Builder {

  private String id;
  private String title;
  private LocalDate dateTransaction;
  private BigDecimal value;
  private boolean received;
  private RecordCreditorDTO._WalletDTO wallet;

  public RecordCreditorDTOBuilder id(String id) {
    this.id = id;
    return this;
  }

  public RecordCreditorDTOBuilder title(String title) {
    this.title = title;
    return this;
  }

  public RecordCreditorDTOBuilder dateTransaction(LocalDate dateTransaction) {
    this.dateTransaction = dateTransaction;
    return this;
  }

  public RecordCreditorDTOBuilder value(BigDecimal value) {
    this.value = value;
    return this;
  }

  public RecordCreditorDTOBuilder received(boolean received) {
    this.received = received;
    return this;
  }

  public RecordCreditorDTOBuilder wallet(RecordCreditorDTO._WalletDTO wallet) {
    this.wallet = wallet;
    return this;
  }

  @Override
  public RecordCreditorDTO build() {
    return new RecordCreditorDTO(id, title, dateTransaction, value, wallet, received);
  }
}