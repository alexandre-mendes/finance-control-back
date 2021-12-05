package br.com.financecontrol.controller;

import br.com.financecontrol.dto.WalletDTO;
import br.com.financecontrol.dto.WalletSummaryDTO;
import br.com.financecontrol.entity.Wallet;
import br.com.financecontrol.enums.Month;
import br.com.financecontrol.enums.TypeWallet;
import br.com.financecontrol.service.WalletService;
import br.com.financecontrol.util.PageBuilder;
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

import static java.util.Objects.isNull;

@RestController
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
          @RequestParam(required = false, name = "month") final Integer month,
          @RequestParam(required = false, name = "year") final Integer year,
          final Pageable pageable) {
    final var wallets = walletService.findAll(typeWallet, pageable);
    return PageBuilder.createPage(wallets, pageable, w -> modelMapper.map(w, WalletDTO.class));
  }

  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "Exclui uma carteira.", authorizations = {@Authorization(value = "Bearer")})
  public void remove(@PathVariable final String id) {
    walletService.remove(id);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/summary")
  @ApiOperation(value = "Obtem um resumo das carteiras no mês atual", authorizations = {@Authorization(value = "Bearer")})
  public WalletSummaryDTO findWalletsSummary(
          @RequestParam(name = "month", required = false) final Integer month,
          @RequestParam(name = "year", required = false) final Integer year) {
    final LocalDate date = LocalDate.now();
    return walletService.findWalletsSummary(
            isNull(month) ? date.getMonthValue() : month, isNull(year) ? date.getYear() : year);
  }
}
