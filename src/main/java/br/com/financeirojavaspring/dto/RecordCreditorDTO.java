package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.RecordCreditorDTOBuilder;

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
public class RecordCreditorDTO implements Serializable {

  private static final long serialVersionUID = -1627812282904815159L;

  private String uuid;

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

  public static RecordCreditorDTOBuilder builder() {
     return new RecordCreditorDTOBuilder();
  }

  @lombok.Getter
  @lombok.Setter
  public static class _WalletDTO implements Serializable {
    private String uuid;
  }
}
