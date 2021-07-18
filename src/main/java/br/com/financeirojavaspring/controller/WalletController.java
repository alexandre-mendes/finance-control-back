package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.WalletDTO;
import br.com.financeirojavaspring.dto.WalletSummaryDTO;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/wallets")
@Api(value = "Wallet Controller")
public class WalletController {

  private final WalletService walletService;

  @Autowired
  public WalletController(WalletService walletService) {
    this.walletService = walletService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @ApiOperation(value = "Salva uma carteira", authorizations = {@Authorization(value = "Bearer")})
  public WalletDTO createWallet(@RequestBody @Valid WalletDTO walletDTO) {
    return walletService.save(walletDTO);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping
  @ApiOperation(value = "Atualiza uma carteira", authorizations = {@Authorization(value = "Bearer")})
  public WalletDTO updateWallet(@RequestBody @Valid WalletDTO walletDTO) {
    return walletService.update(walletDTO);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/months/{month}/years/{year}")
  @ApiOperation(value = "Obtem todas as carteiras vinculadas a conta do usuário", authorizations = {@Authorization(value = "Bearer")})
  public Page<WalletDTO> findAllWallets(
      @RequestParam(required = false) final TypeWallet typeWallet,
      @PathVariable final Integer month,
      @PathVariable final Integer year,
      final Pageable pageable) {
    return walletService.findAll(typeWallet, month, year, pageable);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/summarys/months/{month}/years/{year}")
  @ApiOperation(value = "Obtem um resumo das carteiras no mês atual", authorizations = {@Authorization(value = "Bearer")})
  public WalletSummaryDTO findWalletsSummary(@PathVariable final Integer month, @PathVariable final Integer year) {
    return walletService.findWalletsSummary(month, year);
  }
}
