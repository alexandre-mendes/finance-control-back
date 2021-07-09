package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.dto.InvitationDTOBuilder;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class InvitationDTO {
    private String uuid;

    public static InvitationDTOBuilder builder() {
        return new InvitationDTOBuilder();
    }
}
