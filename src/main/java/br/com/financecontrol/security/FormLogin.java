package br.com.financecontrol.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@lombok.Getter
@lombok.Setter
public class FormLogin {

    private String username;
    private String password;

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
