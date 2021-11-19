package br.com.financecontrol.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 8095027604824735394L;

    private String id;
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
