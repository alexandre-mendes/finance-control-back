package br.com.financecontrol.security;

import br.com.financecontrol.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TokenDTO authenticate(@RequestBody final FormLogin formLogin) {
        final UsernamePasswordAuthenticationToken login = formLogin.converter();

        try{
            final Authentication authenticate = authenticationManager.authenticate(login);
            final String token = tokenService.generate(authenticate);
            return new TokenDTO(token, "Bearer ");
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException();
        }
    }
}
