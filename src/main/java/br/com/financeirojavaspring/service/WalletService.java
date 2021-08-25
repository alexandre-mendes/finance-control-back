package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.WalletSummaryDTO;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.entity.WalletVW;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import br.com.financeirojavaspring.repository.WalletVWRepository;
import br.com.financeirojavaspring.specification.WalletVWSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final WalletVWRepository walletVWRepository;
  private final UserAuthenticationService authenticationService;
  private final RecordDebtorRepository recordDebtorRepository;
  private final RecordCreditorRepository recordCreditorRepository;

  @Autowired
  public WalletService(WalletRepository walletRepository,
                       WalletVWRepository walletVWRepository, ModelMapper modelMapper,
                       UserAuthenticationService authenticationService,
                       RecordDebtorRepository recordDebtorRepository,
                       RecordCreditorRepository recordCreditorRepository) {
    this.walletRepository = walletRepository;
    this.walletVWRepository = walletVWRepository;
    this.authenticationService = authenticationService;
    this.recordDebtorRepository = recordDebtorRepository;
    this.recordCreditorRepository = recordCreditorRepository;
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

  public Page<WalletVW> findAll(
      final TypeWallet typeWallet,
      final LocalDate firstDate,
      final LocalDate lastDate,
      final Pageable pageable) {
    final var account = authenticationService.getUser().getAccount();

    final var specification = new WalletVWSpecification(account.getId(), firstDate, lastDate, typeWallet);

    return walletVWRepository.findAll(specification, pageable);
  }

  public WalletSummaryDTO findWalletsSummary(final Integer month, final Integer year) {
    var firstMonth = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(1);
    var lastMonth = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(firstMonth.lengthOfMonth());
    var totalPaid = recordDebtorRepository.findTotalPaidByMonth(firstMonth, lastMonth, authenticationService.getUser().getAccount());

    var totalDebtor = recordDebtorRepository
        .findTotalByMonth(firstMonth, lastMonth, authenticationService.getUser().getAccount());
    var totalCreditor = recordCreditorRepository
        .findTotalByTypeWalletAndMonth(authenticationService.getUser().getAccount());
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
}
