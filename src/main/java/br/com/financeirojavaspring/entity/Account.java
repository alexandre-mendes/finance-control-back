package br.com.financeirojavaspring.entity;

import br.com.financeirojavaspring.builder.AccountBuilder;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @Column(name = "id_account")
    private Long id;
    @Column(unique = true)
    private String uuid;

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }
}
