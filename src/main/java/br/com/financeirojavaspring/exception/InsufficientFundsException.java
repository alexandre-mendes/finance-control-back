package br.com.financeirojavaspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException() {
    super("Saldo insuficiente para realizar o pagamento.");
  }
}
