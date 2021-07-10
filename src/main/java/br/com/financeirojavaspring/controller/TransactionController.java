package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.PaymentDTO;
import br.com.financeirojavaspring.dto.TransferDTO;
import br.com.financeirojavaspring.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transaction")
@Api(value = "Payment Controller")
public class TransactionController {

  private final TransactionService service;

  @Autowired
  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/pay")
  @ApiOperation(value = "Realiza um pagamento.", authorizations = {@Authorization(value = "Bearer")})
  public void pay(@RequestBody final PaymentDTO dto) {
    service.pay(dto);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/transfer")
  @ApiOperation(value = "Realiza uma transferência.", authorizations = {@Authorization(value = "Bearer")})
  public void transfer(@RequestBody final TransferDTO dto) {
    service.transfer(dto);
  }
}
