package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class CancellationNotAllowed extends RuntimeException {

    public CancellationNotAllowed(final String message) {super(message);}
}
