package br.com.financecontrol.service.strategy;

import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.repository.RecordCreditorRepository;
import br.com.financecontrol.repository.RecordDebtorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component("PAYMENT")
public class CancellerPayment implements Canceller {

    private final RecordDebtorRepository recordDebtorRepository;
    private final RecordCreditorRepository recordCreditorRepository;

    @Autowired
    public CancellerPayment(RecordDebtorRepository recordDebtorRepository, RecordCreditorRepository recordCreditorRepository) {
        this.recordDebtorRepository = recordDebtorRepository;
        this.recordCreditorRepository = recordCreditorRepository;
    }

    @Override
    public void cancel(final RecordCreditor recordCreditor) {
        final var debtors = recordDebtorRepository.findAll(Example.of(RecordDebtor.builder().payerRecord(recordCreditor).build()));

        debtors.forEach(d -> { d.setPaid(false); d.setPayerRecord(null); });
        recordDebtorRepository.saveAll(debtors);
        recordCreditorRepository.deleteById(recordCreditor.getId());
    }
}
