package br.com.financeirojavaspring.service.strategy;

import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.Transaction;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component("TRANSFER_RECEIVED")
public class CancellerTransferReceived implements Canceller {

    private final RecordCreditorRepository repository;

    @Autowired
    public CancellerTransferReceived(RecordCreditorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void cancel(final RecordCreditor recordCreditor) {
        final var records = repository.findAll(
                Example.of(
                        RecordCreditor.builder()
                                .transaction(
                                        Transaction.builder()
                                                .codeTransaction(recordCreditor.getTransaction().getCodeTransaction())
                                                .build()
                                ).build()));

        repository.deleteAll(records);
    }
}
