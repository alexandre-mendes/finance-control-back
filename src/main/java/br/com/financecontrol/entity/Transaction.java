package br.com.financecontrol.entity;

import br.com.financecontrol.builder.TransactionBuilder;
import br.com.financecontrol.enums.TypeTransaction;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String codeTransaction;

    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }
}
