package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.WalletDTO;
import br.com.financeirojavaspring.dto.WalletSummaryDTO;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.model.Wallet;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

  private final WalletRepository walletRepository;
  private final ModelMapper modelMapper;
  private final UserAuthenticationService authenticationService;
  private final RecordDebtorRepository recordDebtorRepository;

  @Autowired
  public WalletService(WalletRepository walletRepository,
      ModelMapper modelMapper,
      UserAuthenticationService authenticationService,
      RecordDebtorRepository recordDebtorRepository) {
    this.walletRepository = walletRepository;
    this.modelMapper = modelMapper;
    this.authenticationService = authenticationService;
    this.recordDebtorRepository = recordDebtorRepository;
  }

  public WalletDTO save(WalletDTO walletDTO) {
    var account = authenticationService.getUser().getAccount();
    var wallet = modelMapper.map(walletDTO, Wallet.class);
    wallet.setUuid(UUID.randomUUID().toString());
    wallet.setAccount(account);
    wallet = walletRepository.save(wallet);
    return modelMapper.map(wallet, WalletDTO.class);
  }

  public WalletDTO update(WalletDTO walletDTO) {
    var example = new Wallet();
    example.setUuid(walletDTO.getUuid());

    var walletSaved = walletRepository.findOne(
        Example.of(example)).orElseThrow(EntityNotFoundException::new);
    walletSaved.setTitle(walletDTO.getTitle());
    walletSaved.setTypeWallet(walletDTO.getTypeWallet());
    walletSaved = walletRepository.save(walletSaved);
    return modelMapper.map(walletSaved, WalletDTO.class);
  }

  public Page<WalletDTO> findAll(TypeWallet typeWallet) {
    var account = authenticationService.getUser().getAccount();
    var example = new Wallet();
    example.setAccount(account);
    example.setTypeWallet(typeWallet);

    var wallets = walletRepository.findAll(
        Example.of(example));
    var walletsDTO = wallets.stream().map(w -> modelMapper.map(w, WalletDTO.class)).collect(Collectors.toList());
    return new PageImpl<>(walletsDTO);
  }

  public WalletSummaryDTO findWalletsSummary() {
    var firstMonth = LocalDate.now().withDayOfMonth(1);
    var lastMonth = LocalDate.now().withDayOfMonth(firstMonth.lengthOfMonth());
    var totalPaid = recordDebtorRepository.findTotalPaidByMonth(firstMonth, lastMonth);

    var totalDebtor = recordDebtorRepository
        .findTotalByTypeWalletAndMonth(TypeWallet.DEBTOR, firstMonth, lastMonth);
    var totalCreditor = recordDebtorRepository
        .findTotalByTypeWalletAndMonth(TypeWallet.CREDITOR, firstMonth, lastMonth);
    BigDecimal percentageCommitted;
    BigDecimal porcentagePaid;

    try {
      percentageCommitted = totalDebtor.divide(totalCreditor, RoundingMode.DOWN);
    } catch (ArithmeticException | NullPointerException ex) {
      percentageCommitted = BigDecimal.ZERO;
    }

    try {
      porcentagePaid = totalPaid.divide(totalDebtor, RoundingMode.DOWN);
    } catch (ArithmeticException | NullPointerException ex) {
      porcentagePaid = BigDecimal.ZERO;
    }

    var summary = new WalletSummaryDTO();
    summary.setTotalDebtor(totalDebtor != null ? totalDebtor : BigDecimal.ZERO);
    summary.setTotalCreditor(totalCreditor != null ? totalCreditor : BigDecimal.ZERO);
    summary.setPercentageCommitted(percentageCommitted);
    summary.setPercentagePaid(porcentagePaid);

    return summary;
  }
}
