package br.com.financeirojavaspring.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class UserDTO {
    private String uuid;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String passwd;
    @NotNull
    @NotBlank
    private String name;
}
