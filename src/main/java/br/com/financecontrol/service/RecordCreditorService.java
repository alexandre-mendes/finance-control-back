package br.com.financecontrol.service;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.Transaction;
import br.com.financecontrol.enums.TypeTransaction;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.repository.RecordCreditorCriteriaRepository;
import br.com.financecontrol.repository.RecordCreditorRepository;
import br.com.financecontrol.repository.WalletRepository;
import br.com.financecontrol.security.AuthenticationService;
import br.com.financecontrol.specification.RecordCreditorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class RecordCreditorService {

  private final RecordCreditorRepository repository;
  private final RecordCreditorCriteriaRepository criteriaRepository;
  private final WalletRepository walletRepository;
  private final AuthenticationService authenticationService;

  @Autowired
  public RecordCreditorService(
          RecordCreditorRepository repository,
          RecordCreditorCriteriaRepository criteriaRepository, WalletRepository walletRepository, AuthenticationService authenticationService) {
    this.repository = repository;
    this.criteriaRepository = criteriaRepository;
    this.walletRepository = walletRepository;
    this.authenticationService = authenticationService;
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
      final String idWallet,
      final Integer month,
      final Integer year,
      final Pageable pageable) {
    final var specification = new RecordCreditorSpecification(idWallet, month, year);
    return repository.findAll(specification, pageable);
  }

  public BigDecimal findTotal(final String walletId) {
    final Account account = authenticationService.getUser().getAccount();
    return criteriaRepository.findTotal(account, walletId).orElse(BigDecimal.ZERO);
  }
}
