package br.com.financeirojavaspring.controller;


import br.com.financeirojavaspring.dto.RecordDebtorDTO;
import br.com.financeirojavaspring.service.RecordDebtorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("/record-debtor")
@Api(value = "Record Debtor Controller")
public class RecordDebtorController {

  private final RecordDebtorService recordService;

  @Autowired
  public RecordDebtorController(RecordDebtorService recordDebtorService) {
    this.recordService = recordDebtorService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @ApiOperation(value = "Salva um registro ou varios registros se houver mais de 1 parcela.", authorizations = {@Authorization(value = "Bearer")})
  public List<RecordDebtorDTO> create(@RequestBody @Valid RecordDebtorDTO recordDebtorDTO) {
    return recordService.create(recordDebtorDTO);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/wallet/{uuidWallet}")
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordDebtorDTO> findAllRecords(@PathVariable String uuidWallet, Pageable pageable) {
    return recordService.findAllByUuidWallet(uuidWallet, pageable);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/wallet/{uuidWallet}/month/{month}")
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID e um mês.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordDebtorDTO> findAllByMonth(
      @PathVariable String uuidWallet,
      @PathVariable Integer month,
      Pageable pageable) {
    return recordService.findAllByUuidWalletAndDeadlineBetween(uuidWallet, month, pageable);
  }
}
