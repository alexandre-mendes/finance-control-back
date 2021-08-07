package br.com.financeirojavaspring.entity;

import br.com.financeirojavaspring.builder.TransactionBuilder;
import br.com.financeirojavaspring.enums.TypeTransaction;

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
