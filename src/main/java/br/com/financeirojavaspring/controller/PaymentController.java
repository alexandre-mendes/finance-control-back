package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.PaymentDTO;
import br.com.financeirojavaspring.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payment")
@Api(value = "Payment Controller")
public class PaymentController {

  private final PaymentService service;

  @Autowired
  public PaymentController(PaymentService service) {
    this.service = service;
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping
  @ApiOperation(value = "Realiza um pagamento.", authorizations = {@Authorization(value = "Bearer")})
  public void pay(@RequestBody final PaymentDTO dto) {
      service.pay(dto);
  }
}
