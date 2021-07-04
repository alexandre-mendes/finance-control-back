package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.RecordDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.model.Record;
import br.com.financeirojavaspring.model.Wallet;
import br.com.financeirojavaspring.repository.RecordRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class RecordService {

  private final RecordRepository recordRepository;
  private final WalletRepository walletRepository;
  private final ModelMapper modelMapper;

  public RecordService(RecordRepository recordRepository,
      WalletRepository walletRepository,
      ModelMapper modelMapper) {
    this.recordRepository = recordRepository;
    this.walletRepository = walletRepository;
    this.modelMapper = modelMapper;
  }

  public List<RecordDTO> save(RecordDTO recordDTO) {
    List<Record> records = new ArrayList<>();
    var wallet = walletRepository.findOne(
        Example.of(
            Wallet.builder()
                .uuid(recordDTO.getWalletUuid())
                .build())).orElseThrow(EntityNotFoundException::new);
    var registrationCode = UUID.randomUUID().toString();

    for (int i = 0; i < recordDTO.getParcelas().intValue(); i++) {
      records.add(
        Record.builder()
            .valor(recordDTO.getValor().divide(recordDTO.getParcelas(), RoundingMode.DOWN))
            .title(recordDTO.getTitle())
            .deadline(i > 0 ? recordDTO.getDeadline().plusMonths(i) : recordDTO.getDeadline())
            .registrationCode(registrationCode)
            .wallet(wallet)
            .paid(false)
            .uuid(UUID.randomUUID().toString())
            .build()
      );
    }
    return recordRepository.saveAll(records).stream().map(r -> modelMapper.map(r, RecordDTO.class))
        .collect(Collectors.toList());
  }

  public Page<RecordDTO> findAllByUuidWallet(String uuidWallet, Pageable pageable) {
    var records = recordRepository.findAll(
        Example.of(
            Record.builder().uuid(uuidWallet).build()),
        pageable
    ).stream()
        .map(r -> modelMapper.map(r, RecordDTO.class))
        .collect(Collectors.toList());
    return new PageImpl<>(records);
  }

  public Page<RecordDTO> findAllByUuidWalletAndDeadlineBetween(String uuidWallet, Integer mes, Pageable pageable) {
    var firstMonth = LocalDate.now().withMonth(mes).withDayOfMonth(1);
    var lastMonth = LocalDate.now().withMonth(mes).withDayOfMonth(firstMonth.lengthOfMonth());
    var records = recordRepository.findAllfindAllByWalletUuidAndDeadlineBetween(uuidWallet, firstMonth, lastMonth, pageable)
        .stream()
        .map(r -> modelMapper.map(r, RecordDTO.class))
        .collect(Collectors.toList());
    return new PageImpl<>(records);
  }
}
