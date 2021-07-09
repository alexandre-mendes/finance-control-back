package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.RecordCreditorDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.model.RecordCreditor;
import br.com.financeirojavaspring.model.Wallet;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecordCreditorService {

  private final RecordCreditorRepository repository;
  private final WalletRepository walletRepository;
  private final ModelMapper modelMapper;
  private final UserAuthenticationService userAuthenticationService;

  @Autowired
  public RecordCreditorService(
      RecordCreditorRepository repository,
      WalletRepository walletRepository,
      ModelMapper modelMapper,
      UserAuthenticationService userAuthenticationService) {
    this.repository = repository;
    this.walletRepository = walletRepository;
    this.modelMapper = modelMapper;
    this.userAuthenticationService = userAuthenticationService;
  }

  public RecordCreditorDTO create(RecordCreditorDTO dto) {
    var example = new Wallet();
    example.setUuid(dto.getWalletUuid());

    var wallet = walletRepository.findOne(
        Example.of(example)).orElseThrow(EntityNotFoundException::new);
    var entity = modelMapper.map(dto, RecordCreditor.class);
    entity.setUuid(UUID.randomUUID().toString());
    entity.setWallet(wallet);
    entity = repository.save(entity);
    return modelMapper.map(entity, RecordCreditorDTO.class);
  }

  public Page<RecordCreditorDTO> findAllByMonth(
      String uuidWallet,
      Integer month,
      Pageable pageable) {
    var firstMonth = LocalDate.now().withMonth(month).withDayOfMonth(1);
    var lastMonth = LocalDate.now().withMonth(month).withDayOfMonth(firstMonth.lengthOfMonth());
    var entitys = repository.findAllfindAllByWalletUuidAndDateReceivementBetween(
        uuidWallet, firstMonth, lastMonth, pageable)
        .stream()
        .map(entity -> modelMapper.map(entity, RecordCreditorDTO.class))
        .collect(Collectors.toList());
    return new PageImpl<>(entitys);
  }
}
