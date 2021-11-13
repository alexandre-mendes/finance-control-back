package br.com.financecontrol.dto;

import br.com.financecontrol.builder.InvitationDTOBuilder;

import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class InvitationDTO implements Serializable {

    private static final long serialVersionUID = -4772889284105680135L;

    private String uuid;

    public static InvitationDTOBuilder builder() {
        return new InvitationDTOBuilder();
    }
}
