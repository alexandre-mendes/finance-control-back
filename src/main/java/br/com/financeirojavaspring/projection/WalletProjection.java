package br.com.financeirojavaspring.projection;

import br.com.financeirojavaspring.enums.TypeWallet;

import java.math.BigDecimal;

@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
public class WalletProjection {

    private Long id;
    private String uuid;
    private String title;
    private TypeWallet typeWallet;
    private Integer dayWallet;
    private BigDecimal value;
    private BigDecimal valuePaid;
}
