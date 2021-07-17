package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.projection.WalletProjection;
import br.com.financeirojavaspring.dto.WalletDTO;
import br.com.financeirojavaspring.dto.WalletSummaryDTO;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.model.Wallet;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

  private final WalletRepository walletRepository;
  private final ModelMapper modelMapper;
  private final UserAuthenticationService authenticationService;
  private final RecordDebtorRepository recordDebtorRepository;
  private final RecordCreditorRepository recordCreditorRepository;

  @Autowired
  public WalletService(WalletRepository walletRepository,
      ModelMapper modelMapper,
      UserAuthenticationService authenticationService,
      RecordDebtorRepository recordDebtorRepository,
      RecordCreditorRepository recordCreditorRepository) {
    this.walletRepository = walletRepository;
    this.modelMapper = modelMapper;
    this.authenticationService = authenticationService;
    this.recordDebtorRepository = recordDebtorRepository;
    this.recordCreditorRepository = recordCreditorRepository;
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
    var walletSaved = walletRepository.findOne(
        Example.of(
            Wallet.builder()
                .uuid(walletDTO.getUuid())
                .build())
    ).orElseThrow(EntityNotFoundException::new);
    walletSaved.setTitle(walletDTO.getTitle());
    walletSaved.setTypeWallet(walletDTO.getTypeWallet());
    walletSaved = walletRepository.save(walletSaved);
    return modelMapper.map(walletSaved, WalletDTO.class);
  }

  public Page<WalletDTO> findAll(final TypeWallet typeWallet, final Integer month, final Pageable pageable) {
    var firstMonth = LocalDate.now().withMonth(month).withDayOfMonth(1);
    var lastMonth = LocalDate.now().withMonth(month).withDayOfMonth(firstMonth.lengthOfMonth());
    var account = authenticationService.getUser().getAccount();

    var wallets = new ArrayList<WalletProjection>();
    if (typeWallet == null || TypeWallet.CREDITOR.equals(typeWallet))
      wallets.addAll(walletRepository.findAllTypeCreditorWithTotalValueByMonthAndAccount(firstMonth, lastMonth, account.getId()));
    if (typeWallet == null || TypeWallet.DEBTOR.equals(typeWallet))
      wallets.addAll(walletRepository.findAllTypeDebtorWithTotalValueAndTotalPaidByMonthAndAccount(firstMonth, lastMonth, account.getId()));

    return new PageImpl<>(wallets.stream().map(w -> modelMapper.map(w, WalletDTO.class)).collect(
        Collectors.toList()));
  }

  public WalletSummaryDTO findWalletsSummary(final Integer month) {
    var firstMonth = LocalDate.now().withMonth(month).withDayOfMonth(1);
    var lastMonth = LocalDate.now().withMonth(month).withDayOfMonth(firstMonth.lengthOfMonth());
    var totalPaid = recordDebtorRepository.findTotalPaidByMonth(firstMonth, lastMonth, authenticationService.getUser().getAccount());

    var totalDebtor = recordDebtorRepository
        .findTotalByMonth(firstMonth, lastMonth, authenticationService.getUser().getAccount());
    var totalCreditor = recordCreditorRepository
        .findTotalByTypeWalletAndMonth(firstMonth, lastMonth, authenticationService.getUser().getAccount());
    BigDecimal percentageCommitted;
    BigDecimal porcentagePaid;

    try {
      percentageCommitted = totalDebtor.get().divide(totalCreditor.get(), RoundingMode.DOWN);
    } catch (ArithmeticException | NullPointerException | NoSuchElementException ex) {
      percentageCommitted = BigDecimal.ZERO;
    }

    try {
      porcentagePaid = totalPaid.get().divide(totalDebtor.get(), RoundingMode.DOWN);
    } catch (ArithmeticException | NullPointerException | NoSuchElementException ex) {
      porcentagePaid = BigDecimal.ZERO;
    }

    return WalletSummaryDTO.builder()
        .debitBalance(totalDebtor.orElse(BigDecimal.ZERO).subtract(totalPaid.orElse(BigDecimal.ZERO)))
        .totalCreditor(totalCreditor.orElse(BigDecimal.ZERO))
        .percentageCommitted(percentageCommitted)
        .percentagePaid(porcentagePaid)
        .build();
  }
}
