package br.com.financeirojavaspring.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/month")
@Api(value = "Mes Controller")
public class MonthController {

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/current")
  @ApiOperation(value = "Retorna o mês atual.", authorizations = {@Authorization(value = "Bearer")})
  public Integer findCurrentMonth() {
    return LocalDate.now().getMonthValue();
  }
}
