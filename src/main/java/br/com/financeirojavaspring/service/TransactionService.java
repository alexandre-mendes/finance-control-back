package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.PaymentDTO;
import br.com.financeirojavaspring.dto.TransferDTO;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.RecordDebtor;
import br.com.financeirojavaspring.entity.Transaction;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.enums.TypeTransaction;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.exception.InsufficientFundsException;
import br.com.financeirojavaspring.exception.NonExistentDebtorException;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import br.com.financeirojavaspring.service.strategy.Canceller;
import br.com.financeirojavaspring.specification.RecordDebtorSpecification;
import br.com.financeirojavaspring.util.Preconditions;
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
  private final WalletRepository walletRepository;
  private final Map<String, Canceller> cancellersCreditor;

  @Autowired
  public TransactionService(
          RecordDebtorRepository recordDebtorRepository,
          RecordCreditorRepository recordCreditorRepository,
          WalletRepository walletRepository, Map<String, Canceller> cancellersCreditor) {
    this.recordDebtorRepository = recordDebtorRepository;
    this.recordCreditorRepository = recordCreditorRepository;
    this.walletRepository = walletRepository;
    this.cancellersCreditor = cancellersCreditor;
  }

  public void pay(PaymentDTO dto) {
    final var recordDebtor = recordDebtorRepository.findOne(
        Example.of(
            RecordDebtor.builder()
                .uuid(dto.getUuidRecordDebtor())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    final var walletCreditor = walletRepository.findByUUID(dto.getUuidWalletCreditor())
        .orElseThrow(EntityNotFoundException::new);

    Preconditions.checkTrue(walletCreditor.getValue().equals(recordDebtor.getValue())
          || walletCreditor.getValue().compareTo(recordDebtor.getValue()) > 0)
        .orElseThrow(InsufficientFundsException::new);


    final var newRecordCreditor = RecordCreditor.builder()
        .uuid(UUID.randomUUID().toString())
        .title("Pagamento para a carteira " + recordDebtor.getWallet().getTitle() + "/" + recordDebtor.getTitle())
        .dateTransaction(LocalDate.now()).value(recordDebtor.getValue().negate())
        .transaction(
                Transaction.builder()
                        .uuid(UUID.randomUUID().toString())
                        .typeTransaction(TypeTransaction.PAYMENT)
                        .codeTransaction(UUID.randomUUID().toString())
                        .build()
        ).debtorsPayd(Collections.singletonList(recordDebtor))
        .wallet(Wallet.builder().id(walletCreditor.getIdWallet()).build())
        .build();

    final var recordSaved = recordCreditorRepository.save(newRecordCreditor);
    recordDebtor.setPaid(true);
    recordDebtor.setPayerRecord(recordSaved);
    recordDebtorRepository.save(recordDebtor);
  }

  public void transfer(TransferDTO dto) {
    final var walletOrigin = walletRepository.findByUUID(dto.getUuidOrigin()).orElseThrow(EntityNotFoundException::new);

    final var walletDestiny = walletRepository.findByUUID(dto.getUuidDestiny()).orElseThrow(EntityNotFoundException::new);

    Preconditions.checkTrue(walletOrigin.getValue().compareTo(dto.getValueTransfer()) == 0
          || walletOrigin.getValue().compareTo(dto.getValueTransfer()) > 0)
        .orElseThrow(InsufficientFundsException::new);

    final var codeTransaction = UUID.randomUUID().toString();

    recordCreditorRepository.saveAll(
        Arrays.asList(
            RecordCreditor.builder()
                .uuid(UUID.randomUUID().toString())
                .dateTransaction(LocalDate.now())
                .value(dto.getValueTransfer().negate())
                .title("Transferência para a carteira " + walletDestiny.getTitle())
                .transaction(
                        Transaction.builder()
                                .uuid(UUID.randomUUID().toString())
                                .typeTransaction(TypeTransaction.TRANSFER)
                                .codeTransaction(codeTransaction)
                                .build()
                )
                .wallet(Wallet.builder().id(walletOrigin.getIdWallet()).build())
                .build(),
            RecordCreditor.builder()
                .uuid(UUID.randomUUID().toString())
                .dateTransaction(LocalDate.now())
                .value(dto.getValueTransfer())
                .title("Transferência recebida da carteira " + walletOrigin.getTitle())
                .transaction(
                        Transaction.builder()
                                .uuid(UUID.randomUUID().toString())
                                .typeTransaction(TypeTransaction.TRANSFER)
                                .codeTransaction(codeTransaction)
                                .build()
                )
                .wallet(Wallet.builder().id(walletDestiny.getIdWallet()).build())
                .build()));
  }

  public void calcelPayment(final String uuidCreditor) {
    final var record = recordCreditorRepository.findOne(
            Example.of(
                    RecordCreditor.builder()
                            .uuid(uuidCreditor)
                            .build())
    ).orElseThrow(EntityNotFoundException::new);

    this.cancellersCreditor.get(record.getTransaction().getTypeTransaction().name()).cancel(record);
  }

  public void payAll(final String uuidWalletDebtor, final String uuidWalletCreditor, final Integer month, final Integer year) {
    final var firstDate = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(1);
    final var lastDate = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(firstDate.lengthOfMonth());

    final var walletCreditor = walletRepository.findByUUID(uuidWalletCreditor)
            .orElseThrow(EntityNotFoundException::new);

    final var recordsDebtor = recordDebtorRepository.findAll(new RecordDebtorSpecification(uuidWalletDebtor, false, firstDate, lastDate));

    final var totalDebtor = recordsDebtor.stream().map(RecordDebtor::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);

    Preconditions.checkTrue(totalDebtor.compareTo(BigDecimal.ZERO) > 0)
            .orElseThrow(NonExistentDebtorException::new);

    Preconditions.checkTrue(walletCreditor.getValue().compareTo(totalDebtor) == 0
                    || walletCreditor.getValue().compareTo(totalDebtor) > 0)
            .orElseThrow(InsufficientFundsException::new);

    final var newRecordCreditor = RecordCreditor.builder()
            .uuid(UUID.randomUUID().toString())
            .title("Pagamento total da carteira " + recordsDebtor.get(0).getWallet().getTitle())
            .dateTransaction(LocalDate.now())
            .value(totalDebtor.negate())
            .transaction(
                    Transaction.builder()
                            .uuid(UUID.randomUUID().toString())
                            .typeTransaction(TypeTransaction.PAYMENT)
                            .codeTransaction(UUID.randomUUID().toString())
                            .build()
            ).debtorsPayd(recordsDebtor)
            .wallet(Wallet.builder().id(walletCreditor.getIdWallet()).build())
            .build();

    final var recordSaved = recordCreditorRepository.save(newRecordCreditor);
    recordsDebtor.forEach(debtor -> {
      debtor.setPaid(true);
      debtor.setPayerRecord(recordSaved);
    });
    recordDebtorRepository.saveAll(recordsDebtor);
  }
}
