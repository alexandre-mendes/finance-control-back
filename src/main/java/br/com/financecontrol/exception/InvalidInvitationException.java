package br.com.financecontrol.exception;

import br.com.financecontrol.exception.intercept.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidInvitationException extends ApplicationException {

  private static final long serialVersionUID = 5918612314795538519L;

  public InvalidInvitationException(String invitationCode) {
    super(String.format("O código de convite %s é invalido", invitationCode), HttpStatus.BAD_REQUEST);
  }
}
