package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;

public class CancellationNotAllowed extends ApplicationException {

    private static final long serialVersionUID = 675695021694761174L;

    public CancellationNotAllowed(final String message) {
        super(message, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
