package br.com.financeirojavaspring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class RecordDTO {

  private String uuid;
  private String registrationCode;
  private String title;
  private LocalDate deadline;
  private Boolean paid;
  private BigDecimal valor;
  private String walletUuid;
  private BigDecimal parcelas;
}
