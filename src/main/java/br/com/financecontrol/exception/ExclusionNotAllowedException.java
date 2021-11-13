package br.com.financecontrol.exception;

import br.com.financecontrol.exception.intercept.ApplicationException;
import org.springframework.http.HttpStatus;

public class ExclusionNotAllowedException extends ApplicationException {

    private static final long serialVersionUID = 3772638885897006596L;

    public ExclusionNotAllowedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
