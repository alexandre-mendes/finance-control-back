package br.com.financeirojavaspring.dto;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class UserDTO {
    private String uuid;
    private String username;
    private String passwd;
    private String name;
}
