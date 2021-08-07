package br.com.financeirojavaspring.builder;

import br.com.financeirojavaspring.dto.RecordCreditorDTO;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RecordCreditorDTOBuilder implements Builder {

  private String uuid;
  private String title;
  private LocalDate dateTransaction;
  private BigDecimal value;
  private boolean received;
  private String walletUuid;

  public RecordCreditorDTOBuilder uuid(String uuid) {
    this.uuid = uuid;
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

  public RecordCreditorDTOBuilder walletUuid(String walletUuid) {
    this.walletUuid = walletUuid;
    return this;
  }

  @Override
  public RecordCreditorDTO build() {
    return new RecordCreditorDTO(uuid, title, dateTransaction, value, walletUuid, received);
  }
}