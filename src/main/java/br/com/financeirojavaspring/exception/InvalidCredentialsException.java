package br.com.financeirojavaspring.exception;

import br.com.financeirojavaspring.exception.intercept.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApplicationException {

    public InvalidCredentialsException() {
        super("Credenciais inválidas", HttpStatus.FORBIDDEN);
    }
}
