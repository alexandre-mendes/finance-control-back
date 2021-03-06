package br.com.financecontrol.controller;

import br.com.financecontrol.dto.RecordCreditorDTO;
import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.service.RecordCreditorService;
import br.com.financecontrol.util.PageBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/records-creditor")
@Api(value = "Record Creditor Controller")
public class RecordCreditorCotroller {

  private final RecordCreditorService service;
  private final ModelMapper modelMapper;

  public RecordCreditorCotroller(RecordCreditorService service, ModelMapper modelMapper) {
    this.service = service;
    this.modelMapper = modelMapper;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @ApiOperation(value = "Salva um registro de crédito.", authorizations = {@Authorization(value = "Bearer")})
  public RecordCreditorDTO create(@RequestBody @Valid final RecordCreditorDTO dto) {
    var domain = modelMapper.map(dto, RecordCreditor.class);
    domain = service.create(domain);
    return modelMapper.map(domain, RecordCreditorDTO.class);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  @ApiOperation(value = "Obtem os registros de credito.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordCreditorDTO> findAll(
      @RequestParam(required = false, name = "wallet-id") final String walletId,
      @RequestParam(required = false, name = "month") final Integer month,
      @RequestParam(required = false, name = "year") final Integer year,
      final Pageable pageable) {
    final var records = service.findAll(walletId, month, year, pageable);
    return PageBuilder.createPage(records, pageable, r -> modelMapper.map(r, RecordCreditorDTO.class));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/total")
  @ApiOperation(value = "Obtem o saldo total do(s) ativo(s).", authorizations = {@Authorization(value = "Bearer")})
  public BigDecimal total(@RequestParam(name = "wallet-id", required = false) final String walletId) {
    return service.findTotal(walletId);
  }
}
