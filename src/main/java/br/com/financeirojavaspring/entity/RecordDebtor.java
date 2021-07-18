package br.com.financeirojavaspring.entity;

import br.com.financeirojavaspring.builder.entity.RecordDebtorBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_record_debtor")
public class RecordDebtor {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_debtor_seq")
  @Column(name = "id_record_debtor")
  private Long id;

  @Column(unique = true)
  private String uuid;

  private String registrationCode;

  private String title;

  private LocalDate dateDeadline;

  private BigDecimal value;

  private Boolean paid;

  @ManyToOne
  @JoinColumn(name = "id_record_creditor")
  private RecordCreditor payerRecord;

  @ManyToOne
  @JoinColumn(name = "id_wallet")
  private Wallet wallet;

  public static RecordDebtorBuilder builder() {
    return new RecordDebtorBuilder();
  }
}
