package br.com.financecontrol.entity;

import br.com.financecontrol.builder.RecordCreditorBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_record_creditor")
public class RecordCreditor implements Serializable {

  private static final long serialVersionUID = -5587978344103430294L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_creditor_seq")
  @Column(name = "id_record_creditor")
  private Long id;

  @Column(unique = true)
  private String uuid;

  private String title;

  private LocalDate dateTransaction;

  private BigDecimal value;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_transaction")
  private Transaction transaction;

  @OneToMany(mappedBy = "payerRecord", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private List<RecordDebtor> debtorsPayd;

  @ManyToOne
  @JoinColumn(name = "id_wallet")
  private Wallet wallet;

  public static RecordCreditorBuilder builder() {
    return new RecordCreditorBuilder();
  }
}
