package br.com.financecontrol.controller;

import br.com.financecontrol.dto.PaymentAllDTO;
import br.com.financecontrol.dto.PaymentDTO;
import br.com.financecontrol.dto.TransferDTO;
import br.com.financecontrol.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@Api(value = "Payment Controller")
public class TransactionController {

  private final TransactionService service;

  @Autowired
  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/pays")
  @ApiOperation(value = "Realiza um pagamento.", authorizations = {@Authorization(value = "Bearer")})
  public void pay(@RequestBody final PaymentDTO dto) {
    service.pay(dto);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/transfers")
  @ApiOperation(value = "Realiza uma transferência.", authorizations = {@Authorization(value = "Bearer")})
  public void transfer(@RequestBody final TransferDTO dto) {
    service.transfer(dto);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/record-creditor/{uuidCreditor}/cancel")
  @ApiOperation(value = "Cancela uma transação.", authorizations = {@Authorization(value = "Bearer")})
  public void cancelPayment(@PathVariable(name = "uuidCreditor") final String uuidCreditor) {
    service.calcelPayment(uuidCreditor);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/pay-all")
  @ApiOperation(value = "Realiza o pagamento de todos os registros de um determinado mês..", authorizations = {@Authorization(value = "Bearer")})
  public void payAll(@RequestBody @Valid final PaymentAllDTO dto) {
    service.payAll(dto.getUuidWalletDebtor(), dto.getUuidWalletCreditor(), dto.getMonth(), dto.getYear());
  }
}
