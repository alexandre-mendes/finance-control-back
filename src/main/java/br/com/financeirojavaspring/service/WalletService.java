package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.WalletSummaryDTO;
import br.com.financeirojavaspring.entity.Account;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.repository.RecordCreditorCriteriaRepository;
import br.com.financeirojavaspring.repository.RecordDebtorCriteriaRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import br.com.financeirojavaspring.security.AuthenticationService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class WalletService {

  private final WalletRepository walletRepository;
  private final AuthenticationService authenticationService;
  private final RecordDebtorCriteriaRepository recordDebtorCriteriaRepository;
  private final RecordCreditorCriteriaRepository recordCreditorCriteriaRepository;

  public WalletService(WalletRepository walletRepository,
                       AuthenticationService authenticationService,
                       RecordDebtorCriteriaRepository recordDebtorCriteriaRepository,
                       RecordCreditorCriteriaRepository recordCreditorCriteriaRepository) {
    this.walletRepository = walletRepository;
    this.authenticationService = authenticationService;
    this.recordDebtorCriteriaRepository = recordDebtorCriteriaRepository;
    this.recordCreditorCriteriaRepository = recordCreditorCriteriaRepository;
  }

  public Wallet save(Wallet wallet) {
    var account = authenticationService.getUser().getAccount();
    wallet.setUuid(UUID.randomUUID().toString());
    wallet.setAccount(account);
    return walletRepository.save(wallet);
  }

  public Wallet update(Wallet wallet) {
    var walletSaved = walletRepository.findOne(
        Example.of(
            Wallet.builder()
                .uuid(wallet.getUuid())
                .build())
    ).orElseThrow(EntityNotFoundException::new);
    walletSaved.setTitle(wallet.getTitle());
    walletSaved.setTypeWallet(wallet.getTypeWallet());
    walletSaved.setDayWallet(wallet.getDayWallet());
    return walletRepository.save(walletSaved);
  }

  public Page<Wallet> findAll(
      final TypeWallet typeWallet,
      final LocalDate firstDate,
      final LocalDate lastDate,
      final Pageable pageable) {
    final var account = authenticationService.getUser().getAccount();

    var wallets = walletRepository.findAll(
            Example.of(Wallet.builder()
                              .typeWallet(typeWallet)
                              .account(account)
                              .build()), pageable);

    wallets.forEach(w -> {

    });

    return wallets;
  }

  public WalletSummaryDTO findWalletsSummary(final Integer month, final Integer year) {
    final var account = authenticationService.getUser().getAccount();
    final var firstMonth = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(1);
    final var lastMonth = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(firstMonth.lengthOfMonth());
    final var totalPaid = recordDebtorCriteriaRepository.findTotalPaid(firstMonth, lastMonth, account);

    final var totalDebtor = recordDebtorCriteriaRepository.findTotal(firstMonth, lastMonth, account);
    final var totalCreditor = recordCreditorCriteriaRepository.findTotal(account);
    BigDecimal percentageCommitted;
    BigDecimal porcentagePaid;

    try {
      percentageCommitted = totalDebtor.orElse(BigDecimal.ZERO)
          .subtract(totalPaid.orElse(BigDecimal.ZERO))
          .divide(totalCreditor.orElse(BigDecimal.ZERO), RoundingMode.DOWN);
    } catch (ArithmeticException | NullPointerException | NoSuchElementException ex) {
      percentageCommitted = BigDecimal.ZERO;
    }

    try {
      porcentagePaid = totalPaid.orElse(BigDecimal.ZERO).divide(totalDebtor.orElse(BigDecimal.ZERO), RoundingMode.DOWN);
    } catch (ArithmeticException | NullPointerException | NoSuchElementException ex) {
      porcentagePaid = BigDecimal.ZERO;
    }

    return WalletSummaryDTO.builder()
        .debitBalance(totalDebtor.orElse(BigDecimal.ZERO).subtract(totalPaid.orElse(BigDecimal.ZERO)))
        .totalCreditor(totalCreditor.orElse(BigDecimal.ZERO))
        .totalDebtor(totalDebtor.orElse(BigDecimal.ZERO))
        .totalPaid(totalPaid.orElse(BigDecimal.ZERO))
        .percentageCommitted(percentageCommitted)
        .percentagePaid(porcentagePaid)
        .build();
  }

    public void remove(final String uuid) {
      final Wallet wallet = walletRepository.findOne(Example.of(Wallet.builder().uuid(uuid).build()))
              .orElseThrow(EntityNotFoundException::new);
      walletRepository.deleteById(wallet.getId());
    }
}
