package br.com.financecontrol.exception;

import br.com.financecontrol.exception.intercept.ApplicationException;
import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends ApplicationException {

  private static final long serialVersionUID = -3554292784965558127L;

  public InsufficientFundsException() {
    super("Saldo insuficiente para realizar o pagamento.", HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
