package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenericException extends RuntimeException {

    private static final long serialVersionUID = -969350365182239914L;

    public GenericException(final String message) {
        super(message);
    }
}
