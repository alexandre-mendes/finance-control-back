package br.com.financecontrol.service.strategy;

import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.repository.RecordCreditorRepository;
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
