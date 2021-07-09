package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.RecordCreditorDTO;
import br.com.financeirojavaspring.dto.RecordDebtorDTO;
import br.com.financeirojavaspring.model.RecordCreditor;
import br.com.financeirojavaspring.service.RecordCreditorService;
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
@RequestMapping("/record-creditor")
@Api(value = "Record Creditor Controller")
public class RecordCreditorCotroller {

  private final RecordCreditorService service;

  @Autowired
  public RecordCreditorCotroller(RecordCreditorService service) {
    this.service = service;
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID.", authorizations = {@Authorization(value = "Bearer")})
  public RecordCreditorDTO create(@RequestBody  RecordCreditorDTO dto) {
    return service.create(dto);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/wallet/{uuidWallet}/month/{month}")
  @ApiOperation(value = "Obtem os registros de uma carteira a partir de seu UUID e um mês.", authorizations = {@Authorization(value = "Bearer")})
  public Page<RecordCreditorDTO> findAllByMonth(
      @PathVariable String uuidWallet,
      @PathVariable Integer month,
      Pageable pageable) {
    return service.findAllByMonth(uuidWallet, month, pageable);
  }
}
