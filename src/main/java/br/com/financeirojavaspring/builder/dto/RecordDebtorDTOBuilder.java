package br.com.financeirojavaspring.builder.dto;

import br.com.financeirojavaspring.builder.Builder;
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
  private String walletUuid;
  private BigDecimal installments;

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

  public RecordDebtorDTOBuilder walletUuid(String walletUuid) {
    this.walletUuid = walletUuid;
    return this;
  }

  public RecordDebtorDTOBuilder installments(BigDecimal installments) {
    this.installments = installments;
    return this;
  }

  public RecordDebtorDTO build() {
    return new RecordDebtorDTO(uuid, registrationCode, title, deadline, paid, value, walletUuid,
        installments);
  }
}