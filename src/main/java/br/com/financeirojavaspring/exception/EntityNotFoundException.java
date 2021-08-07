package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApplicationException {

  private static final long serialVersionUID = 1683629249045187794L;

  public EntityNotFoundException() {
    super("Entidade não encontrada.", HttpStatus.NOT_FOUND);
  }
}
