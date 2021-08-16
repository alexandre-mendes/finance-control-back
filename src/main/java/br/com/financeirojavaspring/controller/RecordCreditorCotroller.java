package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.RecordCreditorDTO;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.service.RecordCreditorService;
import br.com.financeirojavaspring.util.PageBuilder;
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

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/records-creditor")
@Api(value = "Record Creditor Controller")
public class RecordCreditorCotroller {

  private final RecordCreditorService service;
  private final ModelMapper modelMapper;

  @Autowired
  public RecordCreditorCotroller(RecordCreditorService service, ModelMapper modelMapper) {
    this.service = service;
    this.modelMapper = modelMapper;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @ApiOperation(value = "Salva um registro de crédito.", authorizations = {@Authorization(value = "Bearer")})
  public RecordCreditorDTO create(@RequestBody final RecordCreditorDTO dto) {
    var domain = modelMapper.map(dto, RecordCreditor.class);
    domain = service.create(domain);
    return modelMapper.map(domain, RecordCreditorDTO.class);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  @ApiOperation(value = "Obtem os registros de credito.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordCreditorDTO> findAll(
      @RequestParam(required = false, name = "uuid-wallet") final String uuidWallet,
      @RequestParam(required = false, name = "first-date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate firstDate,
      @RequestParam(required = false, name = "last-date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate lastDate,
      final Pageable pageable) {
    final var records = service.findAll(uuidWallet, firstDate, lastDate, pageable);
    return PageBuilder.createPage(records, pageable, r -> modelMapper.map(r, RecordCreditorDTO.class));
  }
}
