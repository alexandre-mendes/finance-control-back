package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.dto.RecordDebtorDTOBuilder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class RecordDebtorDTO implements Serializable {

  private String uuid;

  private String registrationCode;

  @NotNull
  @NotBlank
  private String title;

  @NotNull
  private LocalDate deadline;

  private Boolean paid;

  @NotNull
  private BigDecimal value;

  @NotNull
  private String walletUuid;

  @NotNull
  private BigDecimal installments;

  public static RecordDebtorDTOBuilder builder() {
    return new RecordDebtorDTOBuilder();
  }
}
