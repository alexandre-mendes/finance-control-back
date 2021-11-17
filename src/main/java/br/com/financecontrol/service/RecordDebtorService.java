package br.com.financecontrol.service;

import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.exception.CancellationNotAllowed;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.repository.RecordDebtorRepository;
import br.com.financecontrol.repository.TagRepository;
import br.com.financecontrol.repository.WalletRepository;
import br.com.financecontrol.specification.RecordDebtorSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class RecordDebtorService {

  private final RecordDebtorRepository recordDebtorRepository;
  private final WalletRepository walletRepository;
  private final TagRepository tagRepository;
  private final ModelMapper modelMapper;

  public RecordDebtorService(RecordDebtorRepository recordDebtorRepository,
                             WalletRepository walletRepository,
                             TagRepository tagRepository,
                             ModelMapper modelMapper) {
    this.recordDebtorRepository = recordDebtorRepository;
    this.walletRepository = walletRepository;
    this.tagRepository = tagRepository;
    this.modelMapper = modelMapper;
  }

  public List<RecordDebtor> create(final RecordDebtor domain, final Integer installments) {
    List<RecordDebtor> recordDebtors = new ArrayList<>();

    var wallet = walletRepository.findById(domain.getWallet().getId())
            .orElseThrow(EntityNotFoundException::new);

    var tag = isNull(domain.getTag()) ? null : tagRepository.findById(domain.getTag().getId())
            .orElseThrow(EntityNotFoundException::new);

    var registrationCode = UUID.randomUUID().toString();

    for (int i = 0; i < installments; i++) {
      recordDebtors.add(
          RecordDebtor.builder()
              .value(domain.getValue())
              .title(domain.getTitle())
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
      final LocalDate firstDate,
      final LocalDate lastDate,
      final Pageable pageable) {
    final var specification = new RecordDebtorSpecification(walletId, null, firstDate, lastDate);
    return recordDebtorRepository.findAll(specification, pageable);
  }

    public void delete(String registrationCode) {
      final var records = recordDebtorRepository.findAll(Example.of(RecordDebtor.builder().registrationCode(registrationCode).build()));
      records.forEach(record -> {
        if (record.getPaid()) {
          throw new CancellationNotAllowed("Não é possível remover um débito com parcelas pagas.");
        }
      });
      recordDebtorRepository.deleteAll(records);
    }
}
