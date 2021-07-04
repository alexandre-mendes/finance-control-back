package br.com.financeirojavaspring.controller;


import br.com.financeirojavaspring.dto.RecordDTO;
import br.com.financeirojavaspring.service.RecordService;
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
@RequestMapping("/record")
@Api(value = "Record Controller")
public class RecordController {

  private final RecordService recordService;

  @Autowired
  public RecordController(RecordService recordService) {
    this.recordService = recordService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @ApiOperation(value = "Salva um registro ou varios registros se houver mais de 1 parcela.", authorizations = {@Authorization(value = "Bearer")})
  public List<RecordDTO> createRecord(@RequestBody @Valid RecordDTO recordDTO) {
    return recordService.save(recordDTO);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/{uuidWallet}")
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordDTO> findAllRecords(@PathVariable String uuidWallet, Pageable pageable) {
    return recordService.findAllByUuidWallet(uuidWallet, pageable);
  }

  @ResponseStatus
  @GetMapping(path = "/{uuidWallet}/mes/{mes}")
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID e um mês.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordDTO> findAllRecordsByMonth(String uuidWallet, Integer mes, Pageable pageable) {
    return recordService.findAllByUuidWalletAndDeadlineBetween(uuidWallet, mes, pageable);
  }
}
