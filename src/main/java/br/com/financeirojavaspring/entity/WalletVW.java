package br.com.financeirojavaspring.entity;

import br.com.financeirojavaspring.converter.MonthAtributeConvterter;
import br.com.financeirojavaspring.enums.Month;
import br.com.financeirojavaspring.enums.TypeWallet;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_wallet_vw")
@IdClass(WalletVW.ID.class)
public class WalletVW implements Serializable {

    private static final long serialVersionUID = -6322869128240002322L;

    @Id
    private Long idWallet;

    @Id
    private String uuid;

    @Id
    @Convert(converter = MonthAtributeConvterter.class)
    private Month monthWallet;

    private String title;

    @Enumerated(EnumType.STRING)
    private TypeWallet typeWallet;

    private Integer dayWallet;

    private BigDecimal value;

    private BigDecimal valuePaid;

    private Long idAccount;

    @lombok.Getter
    @lombok.Setter
    public static class ID implements Serializable {
        private static final long serialVersionUID = -841838543255925627L;
        private Long idWallet;
        private String uuid;
        @Convert(converter = MonthAtributeConvterter.class)
        private Month monthWallet;
    }
}
