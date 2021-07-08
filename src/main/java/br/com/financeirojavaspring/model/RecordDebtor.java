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
@lombok.Builder
@Entity
@Table(name = "financeiro_record")
public class Record {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq")
  @Column(name = "id_record")
  private Long id;

  @Column(unique = true)
  private String uuid;

  private String registrationCode;

  private String title;

  private LocalDate deadline;

  private BigDecimal value;

  private Boolean paid;

  @ManyToOne
  @JoinColumn(name = "id_wallet")
  private Wallet wallet;
}
