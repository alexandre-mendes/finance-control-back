package br.com.financecontrol.builder;

import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.entity.Transaction;
import br.com.financecontrol.entity.Wallet;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RecordCreditorBuilder implements Builder {

  private Long id;
  private String uuid;
  private String title;
  private LocalDate dateTransaction;
  private BigDecimal value;
  private Transaction transaction;
  private List<RecordDebtor> debtorsPayd;
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

  public RecordCreditorBuilder dateTransaction(LocalDate dateTransaction) {
    this.dateTransaction = dateTransaction;
    return this;
  }

  public RecordCreditorBuilder value(BigDecimal value) {
    this.value = value;
    return this;
  }

  public RecordCreditorBuilder transaction(Transaction transaction) {
    this.transaction = transaction;
    return this;
  }

  public RecordCreditorBuilder debtorsPayd(List<RecordDebtor> debtorsPayd) {
    this.debtorsPayd = debtorsPayd;
    return this;
  }

  public RecordCreditorBuilder wallet(Wallet wallet) {
    this.wallet = wallet;
    return this;
  }

  @Override
  public RecordCreditor build() {
    return new RecordCreditor(id, uuid, title, dateTransaction, value, transaction, debtorsPayd, wallet);
  }
}