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
public class RecordCreditorDTO {

  private String uuid;

  @NotNull
  @NotBlank
  private String title;

  @NotNull
  private LocalDate dateReceivement;

  @NotNull
  private BigDecimal value;

  @NotNull
  private String walletUuid;
}
