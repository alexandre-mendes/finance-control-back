package br.com.financecontrol.service;

import br.com.financecontrol.dto.WalletSummaryDTO;
import br.com.financecontrol.entity.Wallet;
import br.com.financecontrol.enums.TypeWallet;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.exception.ExclusionNotAllowedException;
import br.com.financecontrol.repository.RecordCreditorCriteriaRepository;
import br.com.financecontrol.repository.RecordDebtorCriteriaRepository;
import br.com.financecontrol.repository.WalletCriteriaRepository;
import br.com.financecontrol.repository.WalletRepository;
import br.com.financecontrol.security.AuthenticationService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;

import static br.com.financecontrol.enums.TypeDay.FIRST_DAY_MONTH;
import static br.com.financecontrol.enums.TypeDay.LAST_DAY_MONTH;
import static br.com.financecontrol.util.DateCreator.createLocalDate;

@Service
public class WalletService {

  private final WalletRepository walletRepository;
  private final WalletCriteriaRepository walletCriteriaRepository;
  private final AuthenticationService authenticationService;
  private final RecordDebtorCriteriaRepository recordDebtorCriteriaRepository;
  private final RecordCreditorCriteriaRepository recordCreditorCriteriaRepository;

  public WalletService(WalletRepository walletRepository,
                       WalletCriteriaRepository walletCriteriaRepository, AuthenticationService authenticationService,
                       RecordDebtorCriteriaRepository recordDebtorCriteriaRepository,
                       RecordCreditorCriteriaRepository recordCreditorCriteriaRepository) {
    this.walletRepository = walletRepository;
    this.walletCriteriaRepository = walletCriteriaRepository;
    this.authenticationService = authenticationService;
    this.recordDebtorCriteriaRepository = recordDebtorCriteriaRepository;
    this.recordCreditorCriteriaRepository = recordCreditorCriteriaRepository;
  }

  public Wallet save(Wallet wallet) {
    var account = authenticationService.getUser().getAccount();
    wallet.setAccount(account);
    return walletRepository.save(wallet);
  }

  public Wallet update(Wallet wallet) {
    var walletSaved = walletRepository.findById(wallet.getId())
            .orElseThrow(EntityNotFoundException::new);
    walletSaved.setTitle(wallet.getTitle());
    walletSaved.setTypeWallet(wallet.getTypeWallet());
    walletSaved.setDayWallet(wallet.getDayWallet());
    return walletRepository.save(walletSaved);
  }

  public Page<Wallet> findAll(
          final TypeWallet typeWallet,
          final Pageable pageable) {
    final var account = authenticationService.getUser().getAccount();

    return walletRepository.findAll(
            Example.of(
                    Wallet.builder()
                            .account(account)
                            .typeWallet(typeWallet)
                            .build()), pageable);
  }

  public WalletSummaryDTO findWalletsSummary(final Integer month, final Integer year) {
    final var account = authenticationService.getUser().getAccount();
    final var firstMonth = createLocalDate(month, year, FIRST_DAY_MONTH);
    final var lastMonth = createLocalDate(month, year, LAST_DAY_MONTH);
    final var totalPaid = recordDebtorCriteriaRepository.findTotalPaid(firstMonth, lastMonth, account);

    final var totalDebtor = recordDebtorCriteriaRepository.findTotal(firstMonth, lastMonth, account);
    final var totalCreditor = recordCreditorCriteriaRepository.findTotal(account, null);
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

    public void remove(final String id) {
      try {
        walletRepository.deleteById(id);
      } catch (DataIntegrityViolationException ex) {
        throw new ExclusionNotAllowedException("Não é possível excluir uma carteira em uso.");
      }
    }
}
