package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInvitationException extends RuntimeException {

  private static final long serialVersionUID = 5918612314795538519L;

  public InvalidInvitationException(String invitationCode) {
    super(String.format("O código de convite %s é invalido", invitationCode));
  }
}
