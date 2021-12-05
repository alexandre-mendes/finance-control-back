package br.com.financecontrol.service;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.enums.TypeDay;
import br.com.financecontrol.exception.CancellationNotAllowed;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.repository.RecordDebtorCriteriaRepository;
import br.com.financecontrol.repository.RecordDebtorRepository;
import br.com.financecontrol.repository.TagRepository;
import br.com.financecontrol.repository.WalletRepository;
import br.com.financecontrol.security.AuthenticationService;
import br.com.financecontrol.specification.RecordDebtorSpecification;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static br.com.financecontrol.util.DateCreator.createLocalDate;
import static java.util.Objects.isNull;

@Service
public class RecordDebtorService {

  private final RecordDebtorRepository recordDebtorRepository;
  private final RecordDebtorCriteriaRepository criteriaRepository;
  private final WalletRepository walletRepository;
  private final TagRepository tagRepository;
  private final AuthenticationService authenticationService;

  public RecordDebtorService(RecordDebtorRepository recordDebtorRepository,
                             RecordDebtorCriteriaRepository criteriaRepository,
                             WalletRepository walletRepository,
                             TagRepository tagRepository,
                             AuthenticationService authenticationService) {
    this.recordDebtorRepository = recordDebtorRepository;
    this.criteriaRepository = criteriaRepository;
    this.walletRepository = walletRepository;
    this.tagRepository = tagRepository;
    this.authenticationService = authenticationService;
  }

  public List<RecordDebtor> create(final RecordDebtor domain, final Integer installments) {
    final List<RecordDebtor> recordDebtors = new ArrayList<>();

    final var wallet = walletRepository.findById(domain.getWallet().getId())
            .orElseThrow(EntityNotFoundException::new);

    final var tag = isNull(domain.getTag()) ? null : tagRepository.findById(domain.getTag().getId())
            .orElseThrow(EntityNotFoundException::new);

    final var registrationCode = UUID.randomUUID().toString();

    for (int i = 0; i < installments; i++) {
      recordDebtors.add(
          RecordDebtor.builder()
              .value(domain.getValue())
              .title(domain.getTitle())
              .createDate(LocalDateTime.now())
              .dateDeadline(i > 0 ? domain.getDateDeadline().plusMonths(i) : domain.getDateDeadline())
              .registrationCode(registrationCode)
              .wallet(wallet)
              .tag(tag)
              .paid(false)
              .build()
      );
    }
    return recordDebtorRepository.saveAll(recordDebtors);
  }

  public Page<RecordDebtor> findAll(
          final String walletId,
          final Integer month,
          final Integer year,
          final Pageable pageable) {
    final var specification = RecordDebtorSpecification.builder()
            .walletId(walletId)
            .month(month)
            .year(year)
            .build();
    return recordDebtorRepository.findAll(specification, pageable);
  }

  public void delete(final String registrationCode) {
    final var records = recordDebtorRepository.findAll(Example.of(RecordDebtor.builder().registrationCode(registrationCode).build()));
    records.forEach(record -> {
      if (record.getPaid()) {
        throw new CancellationNotAllowed("Não é possível remover um débito com parcelas pagas.");
      }
    });
    recordDebtorRepository.deleteAll(records);
  }

  public BigDecimal findTotal(
          final Integer month,
          final Integer year,
          final String walletId) {
    final Account account = authenticationService.getUser().getAccount();
    return criteriaRepository.findTotal(
            createLocalDate(month, year, TypeDay.FIRST_DAY_MONTH),
            createLocalDate(month, year, TypeDay.LAST_DAY_MONTH),
            account,
            walletId).orElse(BigDecimal.ZERO);
  }
}
