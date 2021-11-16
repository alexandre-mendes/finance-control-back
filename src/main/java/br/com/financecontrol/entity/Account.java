package br.com.financecontrol.entity;

import br.com.financecontrol.builder.AccountBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 2017880951855917024L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }
}
