package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.dto.UserDTOBuilder;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class UserDTO implements Serializable {
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

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }
}
