package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.RecordDTO;
import br.com.financeirojavaspring.model.Record;
import br.com.financeirojavaspring.repository.RecordRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

  private RecordRepository recordRepository;
  private WalletRepository walletRepository;
  private ModelMapper modelMapper;

  public RecordService(RecordRepository recordRepository,
      WalletRepository walletRepository,
      ModelMapper modelMapper) {
    this.recordRepository = recordRepository;
    this.walletRepository = walletRepository;
    this.modelMapper = modelMapper;
  }

  public List<RecordDTO> save(RecordDTO recordDTO) {
    List<Record> records = new ArrayList<>();
    var wallet = walletRepository.findByUuid(recordDTO.getWalletUuid());
    var registrationCode = UUID.randomUUID().toString();

    for (int i = 0; i < recordDTO.getParcelas().intValue(); i++) {
      records.add(
        Record.builder()
            .valor(recordDTO.getValor().divide(recordDTO.getParcelas()))
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

  public List<RecordDTO> findAllByUuidWallet(String uuidWallet) {
    var records = recordRepository.findAllByWalletUuid(uuidWallet);
    return records.stream().map(r -> modelMapper.map(r, RecordDTO.class)).collect(Collectors.toList());
  }
}
