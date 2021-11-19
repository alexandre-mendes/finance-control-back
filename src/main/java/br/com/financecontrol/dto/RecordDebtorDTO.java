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
public class RecordDebtorDTO implements Serializable {

  private static final long serialVersionUID = 7287342681482925095L;

  private String id;

  private String registrationCode;

  @NotNull
  @NotBlank
  private String title;

  @NotNull
  private LocalDate dateDeadline;

  private Boolean paid;

  @NotNull
  private BigDecimal value;

  @NotNull
  private RecordDebtorDTO._WalletDTO wallet;

  private RecordDebtorDTO._TagDTO tag;

  @NotNull
  private Integer installments;

  public static RecordDebtorDTOBuilder builder() {
    return new RecordDebtorDTOBuilder();
  }

  @lombok.Getter
  @lombok.Setter
  public static class _WalletDTO implements Serializable {
    private String id;
  }

  @lombok.Getter
  @lombok.Setter
  public static class _TagDTO implements Serializable {
    private String id;
  }
}
