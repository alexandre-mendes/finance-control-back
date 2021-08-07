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
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/records-debtor")
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
  public List<RecordDebtorDTO> create(@RequestBody @Valid final RecordDebtorDTO recordDebtorDTO) {
    return recordService.create(recordDebtorDTO);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/wallets/{uuidWallet}")
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordDebtorDTO> findAllRecords(@PathVariable final String uuidWallet, final Pageable pageable) {
    return recordService.findAllByUuidWallet(uuidWallet, pageable);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/wallets/{uuidWallet}/months/{month}/years/{year}")
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID, mês e ano.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordDebtorDTO> findAllByMonth(
      @PathVariable final String uuidWallet,
      @PathVariable final Integer month,
      @PathVariable final Integer year,
      final Pageable pageable) {
    return recordService.findAllByUuidWalletAndDeadlineBetween(uuidWallet, month, year, pageable);
  }

  @DeleteMapping(path = "/{registrationCode}")
  public void delete(@PathVariable(name = "registrationCode") String registrationCode) {
    recordService.delete(registrationCode);
  }
}
