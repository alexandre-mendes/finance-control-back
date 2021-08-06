package br.com.financeirojavaspring.service.strategy;

import br.com.financeirojavaspring.entity.RecordCreditor;
import org.springframework.stereotype.Component;

@Component
public interface Canceller {
    void cancel(final RecordCreditor recordCreditor);
}
