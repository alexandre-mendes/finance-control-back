package br.com.financecontrol.dto;

import br.com.financecontrol.builder.RecordDebtorDTOBuilder;
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

  private static final long serialVersionUID = 7287342681482925095L;

  private String uuid;

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
    private String uuid;
  }

  @lombok.Getter
  @lombok.Setter
  public static class _TagDTO implements Serializable {
    private String uuid;
  }
}
