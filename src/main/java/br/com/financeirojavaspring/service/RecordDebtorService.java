package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.RecordDebtorDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.entity.RecordDebtor;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecordDebtorService {

  private final RecordDebtorRepository recordDebtorRepository;
  private final WalletRepository walletRepository;
  private final ModelMapper modelMapper;

  public RecordDebtorService(RecordDebtorRepository recordDebtorRepository,
      WalletRepository walletRepository,
      ModelMapper modelMapper) {
    this.recordDebtorRepository = recordDebtorRepository;
    this.walletRepository = walletRepository;
    this.modelMapper = modelMapper;
  }

  public List<RecordDebtorDTO> create(final RecordDebtorDTO recordDebtorDTO) {
    List<RecordDebtor> recordDebtors = new ArrayList<>();
    var wallet = walletRepository.findOne(
        Example.of(
            Wallet.builder()
                .uuid(recordDebtorDTO.getWalletUuid())
                .build()))
        .orElseThrow(EntityNotFoundException::new);
    var registrationCode = UUID.randomUUID().toString();

    for (int i = 0; i < recordDebtorDTO.getInstallments().intValue(); i++) {
      recordDebtors.add(
          RecordDebtor.builder()
              .value(recordDebtorDTO.getValue().divide(recordDebtorDTO.getInstallments(), RoundingMode.DOWN))
              .title(recordDebtorDTO.getTitle())
              .dateDeadline(i > 0 ? recordDebtorDTO.getDateDeadline().plusMonths(i) : recordDebtorDTO.getDateDeadline())
              .registrationCode(registrationCode)
              .wallet(wallet)
              .paid(false)
              .uuid(UUID.randomUUID().toString())
              .build()
      );
    }
    return recordDebtorRepository
        .saveAll(recordDebtors).stream().map(r -> modelMapper.map(r, RecordDebtorDTO.class))
        .collect(Collectors.toList());
  }

  public Page<RecordDebtorDTO> findAllByUuidWallet(final String uuidWallet, final Pageable pageable) {
    var record = RecordDebtor.builder()
        .wallet(
            Wallet.builder()
                .uuid(uuidWallet)
                .build()
        ).build();

    var records = recordDebtorRepository.findAll(
        Example.of(record),
        pageable
    ).stream()
        .map(r -> modelMapper.map(r, RecordDebtorDTO.class))
        .collect(Collectors.toList());
    return new PageImpl<>(records);
  }

  public Page<RecordDebtorDTO> findAllByUuidWalletAndDeadlineBetween(
      final String uuidWallet,
      final Integer month,
      final Integer year,
      final Pageable pageable) {
    var firstMonth = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(1);
    var lastMonth = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(firstMonth.lengthOfMonth());
    var records = recordDebtorRepository
        .findAllfindAllByWalletUuidAndDateDeadlineBetween(uuidWallet, firstMonth, lastMonth, pageable)
        .stream()
        .map(r -> modelMapper.map(r, RecordDebtorDTO.class))
        .sorted(Comparator.comparing(RecordDebtorDTO::getDateDeadline))
        .collect(Collectors.toList());
    return new PageImpl<>(records);
  }
}
