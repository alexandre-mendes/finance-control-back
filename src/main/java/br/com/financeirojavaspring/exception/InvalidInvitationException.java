package br.com.financeirojavaspring.exception;

import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInvitationException extends RuntimeException {

  public InvalidInvitationException(String invitationCode) {
    super(String.format("O código de convite %s é invalido", invitationCode));
  }
}
