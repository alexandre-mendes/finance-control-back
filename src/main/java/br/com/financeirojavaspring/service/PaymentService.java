package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.PaymentDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.exception.InsufficientFundsException;
import br.com.financeirojavaspring.model.RecordCreditor;
import br.com.financeirojavaspring.model.RecordDebtor;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import br.com.financeirojavaspring.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private final RecordDebtorRepository recordDebtorRepository;
  private final RecordCreditorRepository recordCreditorRepository;

  @Autowired
  public PaymentService(
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
}
