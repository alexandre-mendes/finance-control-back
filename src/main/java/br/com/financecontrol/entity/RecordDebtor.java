package br.com.financecontrol.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
@Entity
@Table(name = "financeiro_record_debtor")
public class RecordDebtor implements Serializable {

  private static final long serialVersionUID = -4643213106340314847L;

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String registrationCode;

  private String title;

  private LocalDate dateDeadline;

  private BigDecimal value;

  private Boolean paid;

  @ManyToOne
  @JoinColumn(name = "id_proof_payment")
  private ProofPayment proofPayment;

  @ManyToOne
  @JoinColumn(name = "id_tag")
  private Tag tag;

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
