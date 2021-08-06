package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.RecordCreditorDTO;
import br.com.financeirojavaspring.service.RecordCreditorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/records-creditor")
@Api(value = "Record Creditor Controller")
public class RecordCreditorCotroller {

  private final RecordCreditorService service;

  @Autowired
  public RecordCreditorCotroller(RecordCreditorService service) {
    this.service = service;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @ApiOperation(value = "Salva um registro de crédito.", authorizations = {@Authorization(value = "Bearer")})
  public RecordCreditorDTO create(@RequestBody final RecordCreditorDTO dto) {
    return service.create(dto);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/wallets/{uuidWallet}/months/{month}/years/{year}")
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID, mês e ano.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordCreditorDTO> findAllByMonth(
      @PathVariable final String uuidWallet,
      @PathVariable final Integer month,
      @PathVariable final Integer year,
      final Pageable pageable) {
    return service.findAllByMonth(uuidWallet, month, year, pageable);
  }
}
