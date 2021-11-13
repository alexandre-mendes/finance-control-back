package br.com.financecontrol.controller;


import br.com.financecontrol.dto.RecordDebtorDTO;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.service.RecordDebtorService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/records-debtor")
@Api(value = "Record Debtor Controller")
public class RecordDebtorController {

  private final RecordDebtorService recordService;
  private final ModelMapper modelMapper;

  @Autowired
  public RecordDebtorController(RecordDebtorService recordDebtorService, ModelMapper modelMapper) {
    this.recordService = recordDebtorService;
    this.modelMapper = modelMapper;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @ApiOperation(value = "Salva um registro ou varios registros se houver mais de 1 parcela.", authorizations = {@Authorization(value = "Bearer")})
  public List<RecordDebtorDTO> create(@RequestBody @Valid final RecordDebtorDTO dto) {
    final var domain = modelMapper.map(dto, RecordDebtor.class);
    return recordService.create(domain, dto.getInstallments()).stream().map(r -> modelMapper.map(r, RecordDebtorDTO.class)).collect(Collectors.toList());
  }

  @GetMapping
  @ApiOperation(value = "Obtem os registros de uma carteira.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordDebtorDTO> findAllByMonth(
      @RequestParam(required = false, name = "uuid-wallet") final String uuidWallet,
      @RequestParam(required = false, name = "first-date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate firstDate,
      @RequestParam(required = false, name = "last-date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate lastDate,
      final Pageable pageable) {
    final var records = recordService.findAll(uuidWallet, firstDate, lastDate, pageable);
    return PageBuilder.createPage(records, pageable, r -> modelMapper.map(r, RecordDebtorDTO.class));
  }

  @DeleteMapping(path = "/registration-code/{registrationCode}")
  @ApiOperation(value = "Remove todas as parcelas de um débito.", authorizations = {@Authorization(value = "Bearer")})
  public void delete(@PathVariable(name = "registrationCode") String registrationCode) {
    recordService.delete(registrationCode);
  }
}
