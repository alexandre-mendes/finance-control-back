package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class CancellationNotAllowed extends RuntimeException {

    private static final long serialVersionUID = 675695021694761174L;

    public CancellationNotAllowed(final String message) {super(message);}
}
