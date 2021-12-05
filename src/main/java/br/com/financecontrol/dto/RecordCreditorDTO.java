package br.com.financecontrol.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
public class RecordCreditorDTO implements Serializable {

  private static final long serialVersionUID = -1627812282904815159L;

  private String id;

  @NotNull
  @NotBlank
  private String title;

  @NotNull
  private LocalDate dateTransaction;

  @NotNull
  private BigDecimal value;

  @NotNull
  private RecordCreditorDTO._WalletDTO wallet;

  private boolean received;

  @lombok.Getter
  @lombok.Setter
  public static class _WalletDTO implements Serializable {

    @NotNull
    private String id;
  }
}
