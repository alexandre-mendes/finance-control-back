package br.com.financecontrol.service.strategy;

import br.com.financecontrol.entity.RecordCreditor;
import org.springframework.stereotype.Component;

@Component
public interface Canceller {
    void cancel(final RecordCreditor recordCreditor);
}
