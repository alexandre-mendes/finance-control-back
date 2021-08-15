package br.com.financeirojavaspring.builder;

import br.com.financeirojavaspring.dto.RecordDebtorDTO;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RecordDebtorDTOBuilder implements Builder {

  private String uuid;
  private String registrationCode;
  private String title;
  private LocalDate deadline;
  private Boolean paid;
  private BigDecimal value;
  private RecordDebtorDTO._WalletDTO wallet;
  private Integer installments;

  public RecordDebtorDTOBuilder setUuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public RecordDebtorDTOBuilder setRegistrationCode(String registrationCode) {
    this.registrationCode = registrationCode;
    return this;
  }

  public RecordDebtorDTOBuilder setTitle(String title) {
    this.title = title;
    return this;
  }

  public RecordDebtorDTOBuilder deadline(LocalDate deadline) {
    this.deadline = deadline;
    return this;
  }

  public RecordDebtorDTOBuilder paid(Boolean paid) {
    this.paid = paid;
    return this;
  }

  public RecordDebtorDTOBuilder value(BigDecimal value) {
    this.value = value;
    return this;
  }

  public RecordDebtorDTOBuilder wallet(RecordDebtorDTO._WalletDTO wallet) {
    this.wallet = wallet;
    return this;
  }

  public RecordDebtorDTOBuilder installments(Integer installments) {
    this.installments = installments;
    return this;
  }

  public RecordDebtorDTO build() {
    return new RecordDebtorDTO(uuid, registrationCode, title, deadline, paid, value, wallet,
        installments);
  }
}