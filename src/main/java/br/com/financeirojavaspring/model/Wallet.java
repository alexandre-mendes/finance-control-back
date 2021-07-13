package br.com.financeirojavaspring.model;

import br.com.financeirojavaspring.builder.entity.WalletBuilder;
import br.com.financeirojavaspring.enums.TypeWallet;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_wallet")
public class Wallet implements Serializable {

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
    private List<RecordDebtor> recordDebtor;

    @OneToMany(mappedBy = "wallet")
    private List<RecordDebtor> recordCreditor;

    private Integer dayWallet;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    public static WalletBuilder builder() {
        return new WalletBuilder();
    }
}
