package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.RecordDebtorDTO;
import br.com.financeirojavaspring.entity.RecordDebtor;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.exception.CancellationNotAllowed;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.repository.TagRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import br.com.financeirojavaspring.specification.RecordDebtorSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    var wallet = walletRepository.findByUuid(domain.getWallet().getUuid())
            .orElseThrow(EntityNotFoundException::new);

    var tag = tagRepository.findByUuid(domain.getTag().getUuid())
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
              .uuid(UUID.randomUUID().toString())
              .build()
      );
    }
    return recordDebtorRepository.saveAll(recordDebtors);
  }

  public Page<RecordDebtor> findAll(
      final String uuidWallet,
      final LocalDate firstDate,
      final LocalDate lastDate,
      final Pageable pageable) {
    final var specification = new RecordDebtorSpecification(uuidWallet, null, firstDate, lastDate);
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
