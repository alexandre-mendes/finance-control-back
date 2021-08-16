package br.com.financeirojavaspring.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class PaymentAllDTO implements Serializable {

    private static final long serialVersionUID = 9080374664658533546L;

    @NotNull
    private String uuidWalletDebtor;

    @NotNull
    private String uuidWalletCreditor;

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;
}
