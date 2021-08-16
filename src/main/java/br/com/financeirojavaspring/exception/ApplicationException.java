package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.Setter
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 8942958119740271805L;

    private final HttpStatus httpStatus;

    public ApplicationException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
