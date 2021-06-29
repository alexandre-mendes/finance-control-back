package br.com.financeirojavaspring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class RecordDTO {

  private String uuid;
  private String registrationCode;
  @NotNull
  @NotBlank
  private String title;
  @NotNull
  @NotBlank
  private LocalDate deadline;
  @NotNull
  @NotBlank
  private Boolean paid;
  @NotNull
  @NotBlank
  private BigDecimal valor;
  @NotNull
  @NotBlank
  private String walletUuid;
  @NotNull
  @NotBlank
  private BigDecimal parcelas;
}
