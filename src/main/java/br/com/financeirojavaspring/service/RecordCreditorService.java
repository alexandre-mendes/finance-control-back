package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.RecordCreditorDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.WalletRepository;
import java.time.LocalDate;
import java.util.Comparator;
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

  @Autowired
  public RecordCreditorService(
      RecordCreditorRepository repository,
      WalletRepository walletRepository,
      ModelMapper modelMapper,
      UserAuthenticationService userAuthenticationService) {
    this.repository = repository;
    this.walletRepository = walletRepository;
    this.modelMapper = modelMapper;
  }

  public RecordCreditorDTO create(RecordCreditorDTO dto) {

    var wallet = walletRepository.findOne(
        Example.of(
            Wallet.builder()
                .uuid(dto.getWalletUuid())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    var entity = modelMapper.map(dto, RecordCreditor.class);
    entity.setUuid(UUID.randomUUID().toString());
    entity.setWallet(wallet);
    entity = repository.save(entity);
    return modelMapper.map(entity, RecordCreditorDTO.class);
  }

  public Page<RecordCreditorDTO> findAllByMonth(
      final String uuidWallet,
      final Integer month,
      final Integer year,
      final Pageable pageable) {
    var firstMonth = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(1);
    var lastMonth = LocalDate.now().withMonth(month).withYear(year).withDayOfMonth(firstMonth.lengthOfMonth());
    var dtos = repository.findAllfindAllByWalletUuidAndDateReceivementBetween(
        uuidWallet, firstMonth, lastMonth, pageable)
        .stream()
        .map(entity -> modelMapper.map(entity, RecordCreditorDTO.class))
        .peek(dto -> {
          if(!dto.getDateReceivement().isAfter(LocalDate.now())) {
            dto.setReceived(true);
          }
        }).sorted(Comparator.comparing(RecordCreditorDTO::getDateReceivement)).collect(Collectors.toList());
    return new PageImpl<>(dtos);
  }
}
