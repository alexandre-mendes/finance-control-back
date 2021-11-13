package br.com.financecontrol.service.strategy;

import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.Transaction;
import br.com.financecontrol.repository.RecordCreditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component("TRANSFER")
public class CancellerTransfer implements Canceller {

    private final RecordCreditorRepository repository;

    @Autowired
    public CancellerTransfer(RecordCreditorRepository repository) {
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
