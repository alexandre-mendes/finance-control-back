package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.service.DateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/date")
@Api(value = "Mes Controller")
public class DateController {

  private final DateService service;

  @Autowired
  public DateController(
          DateService service) {
    this.service = service;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/month/current")
  @ApiOperation(value = "Retorna o mês atual.", authorizations = {@Authorization(value = "Bearer")})
  public Integer findCurrentMonth() {
    return service.findCurrentMonth();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/years")
  @ApiOperation(value = "Retorna uma lista contendo todos os anos existentes para os records cadastrados.",
      authorizations = {@Authorization(value = "Bearer")})
  public Page<Integer> findAllYears(final Pageable pageable) {
    return service.findAllYears(pageable);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/years/current")
  @ApiOperation(value = "Retorna o ano atual.",
      authorizations = {@Authorization(value = "Bearer")})
  public Integer findCurrentYear() {
    return service.findCurrentYear();
  }
}
