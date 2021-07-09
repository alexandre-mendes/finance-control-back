package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.service.PaymentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

  public void pay(@PathVariable final String uuidDebtor,
      @PathVariable final String uuidCreditor) {
      service.pay(uuidDebtor, uuidCreditor);
  }
}
