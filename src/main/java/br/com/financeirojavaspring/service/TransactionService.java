package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.PaymentDTO;
import br.com.financeirojavaspring.dto.TransferDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.exception.InsufficientFundsException;
import br.com.financeirojavaspring.model.RecordCreditor;
import br.com.financeirojavaspring.model.RecordDebtor;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.util.Preconditions;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final RecordDebtorRepository recordDebtorRepository;
  private final RecordCreditorRepository recordCreditorRepository;

  @Autowired
  public TransactionService(
      RecordDebtorRepository recordDebtorRepository,
      RecordCreditorRepository recordCreditorRepository) {
    this.recordDebtorRepository = recordDebtorRepository;
    this.recordCreditorRepository = recordCreditorRepository;
  }

  public void pay(PaymentDTO dto) {
    var recordDebtor = recordDebtorRepository.findOne(
        Example.of(
            RecordDebtor.builder()
                .uuid(dto.getUuidDebtor())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    var recordCreditor = recordCreditorRepository.findOne(
        Example.of(
            RecordCreditor.builder()
                .uuid(dto.getUuidCreditor())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    Preconditions.checkTrue(recordCreditor.getValue().equals(recordDebtor.getValue())
          || recordCreditor.getValue().compareTo(recordDebtor.getValue()) > 0)
        .orElseThrow(InsufficientFundsException::new);

    recordDebtor.setPaid(true);

    recordCreditor.setValue(
        recordCreditor.getValue().subtract(recordDebtor.getValue())
    );

    recordDebtorRepository.save(recordDebtor);
    recordCreditorRepository.save(recordCreditor);
  }

  public void transfer(TransferDTO dto) {
    var recordOrigin = recordCreditorRepository.findOne(
        Example.of(
            RecordCreditor.builder()
                .uuid(dto.getUuidOrigin())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    var recordDestiny = recordCreditorRepository.findOne(
        Example.of(
            RecordCreditor.builder()
                .uuid(dto.getUuidDestiny())
                .build())
    ).orElseThrow(EntityNotFoundException::new);

    Preconditions.checkTrue(recordOrigin.getValue().equals(dto.getValueTransfer())
          || recordOrigin.getValue().compareTo(dto.getValueTransfer()) > 0)
        .orElseThrow(InsufficientFundsException::new);

    recordOrigin.setValue(recordOrigin.getValue().subtract(dto.getValueTransfer()));
    recordDestiny.setValue(recordDestiny.getValue().add(dto.getValueTransfer()));

    recordCreditorRepository.saveAll(Arrays.asList(recordOrigin, recordDestiny));
  }
}
