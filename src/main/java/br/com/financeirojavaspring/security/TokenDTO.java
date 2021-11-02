package br.com.financeirojavaspring.security;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
public class TokenDTO {

    private String token;
    private String type;
}
