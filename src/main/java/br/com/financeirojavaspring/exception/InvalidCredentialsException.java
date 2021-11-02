package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApplicationException {

    public InvalidCredentialsException() {
        super("Credenciais inválidas", HttpStatus.FORBIDDEN);
    }
}
