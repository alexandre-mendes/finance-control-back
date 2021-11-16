package br.com.financecontrol.service;

import br.com.financecontrol.security.AuthenticationService;
import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.Transaction;
import br.com.financecontrol.entity.Wallet;
import br.com.financecontrol.enums.TypeTransaction;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.repository.RecordCreditorRepository;
import br.com.financecontrol.repository.WalletRepository;
import br.com.financecontrol.specification.RecordCreditorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class RecordCreditorService {

  private final RecordCreditorRepository repository;
  private final WalletRepository walletRepository;

  @Autowired
  public RecordCreditorService(
      RecordCreditorRepository repository,
      WalletRepository walletRepository) {
    this.repository = repository;
    this.walletRepository = walletRepository;
  }

  public RecordCreditor create(RecordCreditor domain) {

    var wallet = walletRepository.findById(domain.getWallet().getId())
            .orElseThrow(EntityNotFoundException::new);

    domain.setWallet(wallet);
    domain.setTransaction(
            Transaction.builder()
                    .typeTransaction(TypeTransaction.DEPOSIT)
                    .codeTransaction(UUID.randomUUID().toString())
                    .build()
    );
    return repository.save(domain);
  }

  public Page<RecordCreditor> findAll(
      final String uuidWallet,
      final LocalDate firstDate,
      final LocalDate lastDate,
      final Pageable pageable) {
    final var specification = new RecordCreditorSpecification(uuidWallet, firstDate, lastDate);
    return repository.findAll(specification, pageable);
  }
}
