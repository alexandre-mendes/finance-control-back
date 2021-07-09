package br.com.financeirojavaspring.model;

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
@Table(name = "financeiro_record_creditor")
public class RecordCreditor {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_creditor_seq")
  @Column(name = "id_record_creditor")
  private Long id;

  @Column(unique = true)
  private String uuid;

  private String title;

  private LocalDate dateReceivement;

  private BigDecimal value;

  @ManyToOne
  @JoinColumn(name = "id_wallet")
  private Wallet wallet;
}
