package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.WalletDTO;
import br.com.financeirojavaspring.dto.WalletSummaryDTO;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.service.WalletService;
import br.com.financeirojavaspring.util.PageBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/wallets")
@Api(value = "Wallet Controller")
public class WalletController {

  private final WalletService walletService;
  private final ModelMapper modelMapper;

  public WalletController(WalletService walletService, ModelMapper modelMapper) {
    this.walletService = walletService;
    this.modelMapper = modelMapper;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @ApiOperation(value = "Salva uma carteira", authorizations = {@Authorization(value = "Bearer")})
  public WalletDTO createWallet(@RequestBody @Valid final WalletDTO dto) {
    var domain = modelMapper.map(dto, Wallet.class);
    domain = walletService.save(domain);
    return modelMapper.map(domain, WalletDTO.class);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping
  @ApiOperation(value = "Atualiza uma carteira", authorizations = {@Authorization(value = "Bearer")})
  public WalletDTO updateWallet(@RequestBody @Valid WalletDTO dto) {
    var domain = modelMapper.map(dto, Wallet.class);
    domain = walletService.update(domain);
    return modelMapper.map(domain, WalletDTO.class);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  @ApiOperation(value = "Obtem todas as carteiras vinculadas a conta do usuário", authorizations = {@Authorization(value = "Bearer")})
  public Page<WalletDTO> findAllWallets(
      @RequestParam(required = false, name = "type-wallet") final TypeWallet typeWallet,
      @RequestParam(required = false, name = "first-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)final LocalDate firstDate,
      @RequestParam(required = false, name = "last-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate lastDate,
      final Pageable pageable) {
    final var wallets = walletService.findAll(typeWallet, firstDate, lastDate, pageable);
    return PageBuilder.createPage(wallets, pageable, w -> modelMapper.map(w, WalletDTO.class));
  }

  @DeleteMapping(value = "/{uuid}")
  @ApiOperation(value = "Exclui uma carteira.", authorizations = {@Authorization(value = "Bearer")})
  public void remove(final String uuid) {
    walletService.remove(uuid);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/summarys/months/{month}/years/{year}")
  @ApiOperation(value = "Obtem um resumo das carteiras no mês atual", authorizations = {@Authorization(value = "Bearer")})
  public WalletSummaryDTO findWalletsSummary(@PathVariable final Integer month, @PathVariable final Integer year) {
    return walletService.findWalletsSummary(month, year);
  }
}
