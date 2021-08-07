package br.com.financeirojavaspring.entity;

import br.com.financeirojavaspring.builder.AccountBuilder;
import javax.persistence.*;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_account")
public class Account {

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
