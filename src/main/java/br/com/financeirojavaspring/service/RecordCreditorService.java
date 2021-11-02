package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.security.AuthenticationService;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.Transaction;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.enums.TypeTransaction;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import br.com.financeirojavaspring.specification.RecordCreditorSpecification;
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
      WalletRepository walletRepository,
      AuthenticationService userAuthenticationService) {
    this.repository = repository;
    this.walletRepository = walletRepository;
  }

  public RecordCreditor create(RecordCreditor domain) {

    var wallet = walletRepository.findOne(
        Example.of(
            Wallet.builder()
                .uuid(domain.getWallet().getUuid())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    domain.setUuid(UUID.randomUUID().toString());
    domain.setWallet(wallet);
    domain.setTransaction(
            Transaction.builder()
                    .uuid(UUID.randomUUID().toString())
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
