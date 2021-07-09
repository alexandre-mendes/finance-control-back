package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.RecordDebtorDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.model.RecordDebtor;
import br.com.financeirojavaspring.model.Wallet;
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

  public List<RecordDebtorDTO> create(RecordDebtorDTO recordDebtorDTO) {
    var example = new Wallet();
    example.setUuid(recordDebtorDTO.getWalletUuid());

    List<RecordDebtor> recordDebtors = new ArrayList<>();
    var wallet = walletRepository.findOne(
        Example.of(example)).orElseThrow(EntityNotFoundException::new);
    var registrationCode = UUID.randomUUID().toString();

    for (int i = 0; i < recordDebtorDTO.getInstallments().intValue(); i++) {
      var exampleRecord = new RecordDebtor();
      exampleRecord.setValue(recordDebtorDTO.getValue().divide(recordDebtorDTO.getInstallments(), RoundingMode.DOWN));
      exampleRecord.setTitle(recordDebtorDTO.getTitle());
      exampleRecord.setDateDeadline(i > 0 ? recordDebtorDTO.getDeadline().plusMonths(i) : recordDebtorDTO.getDeadline());
      exampleRecord.setRegistrationCode(registrationCode);
      exampleRecord.setWallet(wallet);
      exampleRecord.setPaid(false);
      exampleRecord.setUuid(UUID.randomUUID().toString());

      recordDebtors.add(exampleRecord);
    }
    return recordDebtorRepository
        .saveAll(recordDebtors).stream().map(r -> modelMapper.map(r, RecordDebtorDTO.class))
        .collect(Collectors.toList());
  }

  public Page<RecordDebtorDTO> findAllByUuidWallet(String uuidWallet, Pageable pageable) {
    var example = new Wallet();
    example.setUuid(uuidWallet);

    var exampleRecord = new RecordDebtor();
    exampleRecord.setWallet(example);

    var records = recordDebtorRepository.findAll(
        Example.of(exampleRecord),
        pageable
    ).stream()
        .map(r -> modelMapper.map(r, RecordDebtorDTO.class))
        .collect(Collectors.toList());
    return new PageImpl<>(records);
  }

  public Page<RecordDebtorDTO> findAllByUuidWalletAndDeadlineBetween(String uuidWallet, Integer mes, Pageable pageable) {
    var firstMonth = LocalDate.now().withMonth(mes).withDayOfMonth(1);
    var lastMonth = LocalDate.now().withMonth(mes).withDayOfMonth(firstMonth.lengthOfMonth());
    var records = recordDebtorRepository
        .findAllfindAllByWalletUuidAndDateDeadlineBetween(uuidWallet, firstMonth, lastMonth, pageable)
        .stream()
        .map(r -> modelMapper.map(r, RecordDebtorDTO.class))
        .sorted(Comparator.comparing(RecordDebtorDTO::getDeadline))
        .collect(Collectors.toList());
    return new PageImpl<>(records);
  }
}
