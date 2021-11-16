package br.com.financecontrol.builder;

import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.entity.Tag;
import br.com.financecontrol.entity.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecordDebtorBuilder implements Builder {

  private String id;
  private String registrationCode;
  private String title;
  private LocalDate dateDeadline;
  private BigDecimal value;
  private Boolean paid;
  private RecordCreditor payerRecord;
  private Tag tag;
  private Wallet wallet;

  public RecordDebtorBuilder id(String id) {
    this.id = id;
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

  public RecordDebtorBuilder tag(Tag tag) {
    this.tag = tag;
    return this;
  }

  public RecordDebtorBuilder payerRecord(RecordCreditor payerRecord) {
    this.payerRecord = payerRecord;
    return this;
  }

  public RecordDebtorBuilder wallet(Wallet wallet) {
    this.wallet = wallet;
    return this;
  }

  @Override
  public RecordDebtor build() {
    return new RecordDebtor(id, registrationCode, title, dateDeadline, value, paid, tag, payerRecord, wallet);
  }
}