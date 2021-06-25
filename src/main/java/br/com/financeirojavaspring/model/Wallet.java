package br.com.financeirojavaspring.model;

import br.com.financeirojavaspring.enums.TypeWallet;

import javax.persistence.*;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @Column(name = "id_wallet")
    private Long id;

    @Column(unique = true)
    private String uuid;

    private String title;

    @Enumerated(EnumType.STRING)
    private TypeWallet typeWallet;

    @OneToMany(mappedBy = "wallet")
    private List<Record> record;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;
}
