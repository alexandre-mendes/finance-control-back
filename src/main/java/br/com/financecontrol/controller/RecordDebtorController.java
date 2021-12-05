package br.com.financecontrol.controller;


import br.com.financecontrol.dto.RecordDebtorDTO;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.service.RecordDebtorService;
import br.com.financecontrol.util.PageBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/records-debtor")
@Api(value = "Record Debtor Controller")
public class RecordDebtorController {

  private final RecordDebtorService recordService;
  private final ModelMapper modelMapper;

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
  public Page<RecordDebtorDTO> findAll(
      @RequestParam(required = false, name = "month") final Integer month,
      @RequestParam(required = false, name = "year") final Integer year,
      @RequestParam(required = false, name = "wallet-id") final String walletId,
      final Pageable pageable) {
    final var records = recordService.findAll(walletId, month, year, pageable);
    return PageBuilder.createPage(records, pageable, r -> modelMapper.map(r, RecordDebtorDTO.class));
  }

  @GetMapping("/total")
  @ApiOperation(value = "Obtem o saldo total do(s) passivo(s).", authorizations = {@Authorization(value = "Bearer")})
  public BigDecimal total(
          @RequestParam(name = "wallet-id", required = false) final String walletId,
          @RequestParam(name = "month", required = false) final Integer month,
          @RequestParam(name = "year", required = false) final Integer year) {
    return recordService.findTotal(month, year, walletId);
  }

  @DeleteMapping(path = "/registration-code/{registrationCode}")
  @ApiOperation(value = "Remove todas as parcelas de um débito.", authorizations = {@Authorization(value = "Bearer")})
  public void delete(@PathVariable(name = "registrationCode") String registrationCode) {
    recordService.delete(registrationCode);
  }
}
