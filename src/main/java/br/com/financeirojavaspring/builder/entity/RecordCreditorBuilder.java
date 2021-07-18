package br.com.financeirojavaspring.builder.entity;

import br.com.financeirojavaspring.builder.Builder;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.Wallet;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RecordCreditorBuilder implements Builder {

  private Long id;
  private String uuid;
  private String title;
  private LocalDate dateReceivement;
  private BigDecimal value;
  private Wallet wallet;

  public RecordCreditorBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public RecordCreditorBuilder uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public RecordCreditorBuilder title(String title) {
    this.title = title;
    return this;
  }

  public RecordCreditorBuilder dateReceivement(LocalDate dateReceivement) {
    this.dateReceivement = dateReceivement;
    return this;
  }

  public RecordCreditorBuilder value(BigDecimal value) {
    this.value = value;
    return this;
  }

  public RecordCreditorBuilder wallet(Wallet wallet) {
    this.wallet = wallet;
    return this;
  }

  @Override
  public RecordCreditor build() {
    return new RecordCreditor(id, uuid, title, dateReceivement, value, wallet);
  }
}