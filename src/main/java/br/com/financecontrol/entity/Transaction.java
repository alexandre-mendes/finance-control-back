package br.com.financecontrol.entity;

import br.com.financecontrol.builder.TransactionBuilder;
import br.com.financecontrol.enums.TypeTransaction;

import javax.persistence.*;
import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@Entity
@Table(name = "financeiro_transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 864739280148743163L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @Column(name = "id_transaction")
    private Long id;

    @Column(unique = true)
    private String uuid;

    private String codeTransaction;

    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }
}
