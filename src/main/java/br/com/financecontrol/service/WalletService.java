package br.com.financecontrol.service;

import br.com.financecontrol.dto.WalletSummaryDTO;
import br.com.financecontrol.entity.Wallet;
import br.com.financecontrol.enums.TypeWallet;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.exception.ExclusionNotAllowedException;
import br.com.financecontrol.projection.WalletProjection;
import br.com.financecontrol.repository.RecordCreditorCriteriaRepository;
import br.com.financecontrol.repository.RecordDebtorCriteriaRepository;
import br.com.financecontrol.repository.WalletCriteriaRepository;
import br.com.financecontrol.repository.WalletRepository;
import br.com.financecontrol.security.AuthenticationService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.NoSuchElementException;

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

  public Page<WalletProjection> findAll(
      final TypeWallet typeWallet,
      final LocalDate firstDate,
      final LocalDate lastDate,
      final Pageable pageable) {
    final var account = authenticationService.getUser().getAccount();

    return walletCriteriaRepository.findAll(account, typeWallet, firstDate, lastDate, pageable);
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

    public void remove(final String id) {
      try {
        walletRepository.deleteById(id);
      } catch (DataIntegrityViolationException ex) {
        throw new ExclusionNotAllowedException("Não é possível excluir uma carteira em uso.");
      }
    }
}
