package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1683629249045187794L;

  public EntityNotFoundException() {
    super("Entidade não encontrada.");
  }
}
