package br.com.financecontrol.dto;

import br.com.financecontrol.builder.UserDTOBuilder;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 8095027604824735394L;

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
