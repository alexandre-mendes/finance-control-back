package br.com.financecontrol.entity;

import br.com.financecontrol.builder.WalletBuilder;
import br.com.financecontrol.enums.TypeWallet;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = -747807130742436193L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String title;

    @Enumerated(EnumType.STRING)
    private TypeWallet typeWallet;

    @OneToMany(mappedBy = "wallet")
    private List<RecordDebtor> recordsDebtor;

    @OneToMany(mappedBy = "wallet")
    private List<RecordCreditor> recordsCreditor;

    private Integer dayWallet;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @Transient
    private BigDecimal value;

    @Transient
    private BigDecimal valuePaid;

    public static WalletBuilder builder() {
        return new WalletBuilder();
    }
}
