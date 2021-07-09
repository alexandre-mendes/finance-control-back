package br.com.financeirojavaspring.builder.entity;

import br.com.financeirojavaspring.builder.Builder;
import br.com.financeirojavaspring.model.RecordDebtor;
import br.com.financeirojavaspring.model.Wallet;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RecordDebtorBuilder implements Builder {

  private Long id;
  private String uuid;
  private String registrationCode;
  private String title;
  private LocalDate dateDeadline;
  private BigDecimal value;
  private Boolean paid;
  private Wallet wallet;

  public RecordDebtorBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public RecordDebtorBuilder uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public RecordDebtorBuilder registrationCode(String registrationCode) {
    this.registrationCode = registrationCode;
    return this;
  }

  public RecordDebtorBuilder title(String title) {
    this.title = title;
    return this;
  }

  public RecordDebtorBuilder dateDeadline(LocalDate dateDeadline) {
    this.dateDeadline = dateDeadline;
    return this;
  }

  public RecordDebtorBuilder value(BigDecimal value) {
    this.value = value;
    return this;
  }

  public RecordDebtorBuilder paid(Boolean paid) {
    this.paid = paid;
    return this;
  }

  public RecordDebtorBuilder wallet(Wallet wallet) {
    this.wallet = wallet;
    return this;
  }

  @Override
  public RecordDebtor build() {
    return new RecordDebtor(id, uuid, registrationCode, title, dateDeadline, value, paid, wallet);
  }
}