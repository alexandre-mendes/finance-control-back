package br.com.financecontrol.dto;

import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
public class InvitationDTO implements Serializable {

    private static final long serialVersionUID = -4772889284105680135L;

    private String id;

    public static InvitationDTOBuilder builder() {
        return new InvitationDTOBuilder();
    }
}
