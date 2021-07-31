package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/date")
@Api(value = "Mes Controller")
public class DateController {

  private final RecordDebtorRepository recordDebtorRepository;

  @Autowired
  public DateController(
      RecordDebtorRepository recordDebtorRepository) {
    this.recordDebtorRepository = recordDebtorRepository;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/month/current")
  @ApiOperation(value = "Retorna o mês atual.", authorizations = {@Authorization(value = "Bearer")})
  public Integer findCurrentMonth() {
    return LocalDate.now().getMonthValue();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/years")
  @ApiOperation(value = "Retorna uma lista contendo todos os anos existentes para os records cadastrados.",
      authorizations = {@Authorization(value = "Bearer")})
  public Page<Integer> findAllYears(final Pageable pageable) {
    final var yers = recordDebtorRepository.findAllYears(pageable);
    return yers.isEmpty() ? new PageImpl<>(Collections.singletonList(LocalDate.now().getYear())) : new PageImpl<>(yers);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/years/current")
  @ApiOperation(value = "Retorna o ano atual.",
      authorizations = {@Authorization(value = "Bearer")})
  public Integer findCurrentYaer() {
    return LocalDate.now().getYear();
  }
}
