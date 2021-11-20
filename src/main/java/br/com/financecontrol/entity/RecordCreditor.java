package br.com.financecontrol.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
@Entity
@Table(name = "record_creditor")
public class RecordCreditor implements Serializable {

  private static final long serialVersionUID = -5587978344103430294L;

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

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
