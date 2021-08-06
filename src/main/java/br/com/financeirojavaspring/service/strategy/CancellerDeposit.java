package br.com.financeirojavaspring.service.strategy;

import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.repository.RecordCreditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DEPOSIT")
public class CancellerDeposit implements Canceller {

    private final RecordCreditorRepository repository;

    @Autowired
    public CancellerDeposit(RecordCreditorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void cancel(final RecordCreditor recordCreditor) {
        repository.deleteById(recordCreditor.getId());
    }
}
