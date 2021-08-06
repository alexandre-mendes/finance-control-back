package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.PaymentDTO;
import br.com.financeirojavaspring.dto.TransferDTO;
import br.com.financeirojavaspring.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
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
}
