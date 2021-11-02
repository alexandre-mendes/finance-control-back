package br.com.financeirojavaspring.exception;

import br.com.financeirojavaspring.exception.intercept.ApplicationException;
import org.springframework.http.HttpStatus;

public class NonExistentDebtorException extends ApplicationException {

    private static final long serialVersionUID = -8217655065513456037L;

    public NonExistentDebtorException() {
        super("Não existem débitos pendentes", HttpStatus.NOT_FOUND);
    }
}
