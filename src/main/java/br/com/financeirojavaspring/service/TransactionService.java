package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.PaymentDTO;
import br.com.financeirojavaspring.dto.TransferDTO;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.exception.InsufficientFundsException;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.RecordDebtor;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import br.com.financeirojavaspring.util.Preconditions;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final RecordDebtorRepository recordDebtorRepository;
  private final RecordCreditorRepository recordCreditorRepository;
  private final WalletRepository walletRepository;

  @Autowired
  public TransactionService(
      RecordDebtorRepository recordDebtorRepository,
      RecordCreditorRepository recordCreditorRepository,
      WalletRepository walletRepository) {
    this.recordDebtorRepository = recordDebtorRepository;
    this.recordCreditorRepository = recordCreditorRepository;
    this.walletRepository = walletRepository;
  }

  public void pay(PaymentDTO dto) {
    var recordDebtor = recordDebtorRepository.findOne(
        Example.of(
            RecordDebtor.builder()
                .uuid(dto.getUuidRecordDebtor())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    var walletCreditor = walletRepository.findByUUID(dto.getUuidWalletCreditor())
        .orElseThrow(EntityNotFoundException::new);

    Preconditions.checkTrue(walletCreditor.getValue().equals(recordDebtor.getValue())
          || walletCreditor.getValue().compareTo(recordDebtor.getValue()) > 0)
        .orElseThrow(InsufficientFundsException::new);

    recordDebtor.setPaid(true);

    var newRecordCreditor = RecordCreditor.builder()
        .uuid(UUID.randomUUID().toString())
        .title("Pagamento para a carteira " + recordDebtor.getWallet().getTitle() + "/" + recordDebtor.getTitle())
        .dateReceivement(LocalDate.now()).value(recordDebtor.getValue().negate())
        .wallet(Wallet.builder().id(walletCreditor.getIdWallet()).build())
        .build();

    recordDebtorRepository.save(recordDebtor);
    recordCreditorRepository.save(newRecordCreditor);
  }

  public void transfer(TransferDTO dto) {
    var walletOrigin = walletRepository.findByUUID(dto.getUuidOrigin()).orElseThrow(EntityNotFoundException::new);

    var walletDestiny = walletRepository.findByUUID(dto.getUuidDestiny()).orElseThrow(EntityNotFoundException::new);

    Preconditions.checkTrue(walletOrigin.getValue().compareTo(dto.getValueTransfer()) == 0
          || walletOrigin.getValue().compareTo(dto.getValueTransfer()) > 0)
        .orElseThrow(InsufficientFundsException::new);

    recordCreditorRepository.saveAll(
        Arrays.asList(
            RecordCreditor.builder()
                .uuid(UUID.randomUUID().toString())
                .dateReceivement(LocalDate.now())
                .value(dto.getValueTransfer().negate())
                .title("Transferência para a carteira " + walletDestiny.getTitle())
                .wallet(Wallet.builder().id(walletOrigin.getIdWallet()).build())
                .build(),
            RecordCreditor.builder()
                .uuid(UUID.randomUUID().toString())
                .dateReceivement(LocalDate.now())
                .value(dto.getValueTransfer())
                .title("Transferência recebida da carteira " + walletOrigin.getTitle())
                .wallet(Wallet.builder().id(walletDestiny.getIdWallet()).build())
                .build()));
  }
}
