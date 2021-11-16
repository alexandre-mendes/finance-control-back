package br.com.financecontrol.projection;

import br.com.financecontrol.enums.TypeWallet;

import java.math.BigDecimal;

@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
public class WalletProjection {

    private String id;
    private String title;
    private TypeWallet typeWallet;
    private Integer dayWallet;
    private BigDecimal value;
    private BigDecimal valuePaid;
}
