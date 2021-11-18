package br.com.financecontrol.service;

import br.com.financecontrol.dto.PaymentDTO;
import br.com.financecontrol.dto.TransferDTO;
import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.entity.Transaction;
import br.com.financecontrol.entity.Wallet;
import br.com.financecontrol.enums.TypeTransaction;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.exception.InsufficientFundsException;
import br.com.financecontrol.exception.NonExistentDebtorException;
import br.com.financecontrol.repository.RecordCreditorRepository;
import br.com.financecontrol.repository.RecordDebtorRepository;
import br.com.financecontrol.repository.WalletCriteriaRepository;
import br.com.financecontrol.service.strategy.Canceller;
import br.com.financecontrol.specification.RecordDebtorSpecification;
import br.com.financecontrol.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionService {

  private final RecordDebtorRepository recordDebtorRepository;
  private final RecordCreditorRepository recordCreditorRepository;
  private final WalletCriteriaRepository walletCriteriaRepository;
  private final Map<String, Canceller> cancellersCreditor;

  @Autowired
  public TransactionService(
          RecordDebtorRepository recordDebtorRepository,
          RecordCreditorRepository recordCreditorRepository,
          WalletCriteriaRepository walletCriteriaRepository,
          Map<String, Canceller> cancellersCreditor) {
    this.recordDebtorRepository = recordDebtorRepository;
    this.recordCreditorRepository = recordCreditorRepository;
    this.walletCriteriaRepository = walletCriteriaRepository;
    this.cancellersCreditor = cancellersCreditor;
  }

  public void pay(PaymentDTO dto) {
    final var recordDebtor = recordDebtorRepository.findOne(
        Example.of(
            RecordDebtor.builder()
                .id(dto.getRecordDebtorId())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    final var walletCreditor = walletCriteriaRepository.findWalletCreditorWithTotal(dto.getWalletCreditorId())
        .orElseThrow(EntityNotFoundException::new);

    Preconditions.checkTrue(walletCreditor.getValue().equals(recordDebtor.getValue())
          || walletCreditor.getValue().compareTo(recordDebtor.getValue()) > 0)
        .orElseThrow(InsufficientFundsException::new);


    final var newRecordCreditor = RecordCreditor.builder()
        .title("Pagamento para a carteira " + recordDebtor.getWallet().getTitle() + "/" + recordDebtor.getTitle())
        .dateTransaction(LocalDate.now()).value(recordDebtor.getValue().negate())
        .transaction(
                Transaction.builder()
                        .typeTransaction(TypeTransaction.PAYMENT)
                        .codeTransaction(UUID.randomUUID().toString())
                        .build()
        ).debtorsPayd(Collections.singletonList(recordDebtor))
        .wallet(Wallet.builder().id(walletCreditor.getId()).build())
        .build();

    final var recordSaved = recordCreditorRepository.save(newRecordCreditor);
    recordDebtor.setPaid(true);
    recordDebtor.setPayerRecord(recordSaved);
    recordDebtorRepository.save(recordDebtor);
  }

  public void transfer(TransferDTO dto) {
    final var walletOrigin = walletCriteriaRepository.findWalletCreditorWithTotal(dto.getOriginId()).orElseThrow(EntityNotFoundException::new);

    final var walletDestiny = walletCriteriaRepository.findWalletCreditorWithTotal(dto.getDestinyId()).orElseThrow(EntityNotFoundException::new);

    Preconditions.checkTrue(walletOrigin.getValue().compareTo(dto.getValueTransfer()) == 0
          || walletOrigin.getValue().compareTo(dto.getValueTransfer()) > 0)
        .orElseThrow(InsufficientFundsException::new);

    final var codeTransaction = UUID.randomUUID().toString();

    recordCreditorRepository.saveAll(
        Arrays.asList(
            RecordCreditor.builder()
                .dateTransaction(LocalDate.now())
                .value(dto.getValueTransfer().negate())
                .title("Transferência para a carteira " + walletDestiny.getTitle())
                .transaction(
                        Transaction.builder()
                                .typeTransaction(TypeTransaction.TRANSFER)
                                .codeTransaction(codeTransaction)
                                .build()
                )
                .wallet(Wallet.builder().id(walletOrigin.getId()).build())
                .build(),
            RecordCreditor.builder()
                .dateTransaction(LocalDate.now())
                .value(dto.getValueTransfer())
                .title("Transferência recebida da carteira " + walletOrigin.getTitle())
                .transaction(
                        Transaction.builder()
                                .typeTransaction(TypeTransaction.TRANSFER)
                                .codeTransaction(codeTransaction)
                                .build()
                )
                .wallet(Wallet.builder().id(walletDestiny.getId()).build())
                .build()));
  }

  public void calcelPayment(final String idCreditor) {
    final var record = recordCreditorRepository.findOne(
            Example.of(
                    RecordCreditor.builder()
                            .id(idCreditor)
                            .build())
    ).orElseThrow(EntityNotFoundException::new);

    this.cancellersCreditor.get(record.getTransaction().getTypeTransaction().name()).cancel(record);
  }

  public void payAll(final String walletDebtorId, final String walletCreditorId, final Integer month, final Integer year) {
    final var firstDate = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(1);
    final var lastDate = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(firstDate.lengthOfMonth());

    final var walletCreditor = walletCriteriaRepository.findWalletCreditorWithTotal(walletCreditorId)
            .orElseThrow(EntityNotFoundException::new);

    final var recordsDebtor = recordDebtorRepository.findAll(new RecordDebtorSpecification(walletDebtorId, false, firstDate, lastDate));

    final var totalDebtor = recordsDebtor.stream().map(RecordDebtor::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);

    Preconditions.checkTrue(totalDebtor.compareTo(BigDecimal.ZERO) > 0)
            .orElseThrow(NonExistentDebtorException::new);

    Preconditions.checkTrue(walletCreditor.getValue().compareTo(totalDebtor) == 0
                    || walletCreditor.getValue().compareTo(totalDebtor) > 0)
            .orElseThrow(InsufficientFundsException::new);

    final var newRecordCreditor = RecordCreditor.builder()
            .title("Pagamento total da carteira " + recordsDebtor.get(0).getWallet().getTitle())
            .dateTransaction(LocalDate.now())
            .value(totalDebtor.negate())
            .transaction(
                    Transaction.builder()
                            .typeTransaction(TypeTransaction.PAYMENT)
                            .codeTransaction(UUID.randomUUID().toString())
                            .build()
            ).debtorsPayd(recordsDebtor)
            .wallet(Wallet.builder().id(walletCreditor.getId()).build())
            .build();

    final var recordSaved = recordCreditorRepository.save(newRecordCreditor);
    recordsDebtor.forEach(debtor -> {
      debtor.setPaid(true);
      debtor.setPayerRecord(recordSaved);
    });
    recordDebtorRepository.saveAll(recordsDebtor);
  }
}
