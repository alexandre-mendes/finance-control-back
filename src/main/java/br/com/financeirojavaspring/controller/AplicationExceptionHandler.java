package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.StandardErrorDTO;
import br.com.financeirojavaspring.exception.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class AplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> handleException(Exception e, HttpServletRequest request) {

        if (e instanceof ApplicationException) {
            StandardErrorDTO standardError = new StandardErrorDTO(Instant.now(),
                    ((ApplicationException) e).getHttpStatus().value(),
                    e.getLocalizedMessage(),
                    e.getMessage(),
                    request.getRequestURI());
            return ResponseEntity.status(standardError.getStatus()).body(standardError);
        }

        StandardErrorDTO standardError = new StandardErrorDTO(Instant.now(),
                500,
                e.getLocalizedMessage(),
                "Ocorreu um erro inesperado!",
                request.getRequestURI());

        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }
}
