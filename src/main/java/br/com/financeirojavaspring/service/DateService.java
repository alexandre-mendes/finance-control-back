package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.repository.RecordDebtorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@Service
public class DateService {

    private final RecordDebtorRepository recordDebtorRepository;
    private final UserAuthenticationService authenticationService;

    public DateService(RecordDebtorRepository recordDebtorRepository, UserAuthenticationService authenticationService) {
        this.recordDebtorRepository = recordDebtorRepository;
        this.authenticationService = authenticationService;
    }


    public Page<Integer> findAllYears(Pageable pageable) {
        final var yers = recordDebtorRepository.findAllYears(pageable);
        return yers.isEmpty() ? new PageImpl<>(Collections.singletonList(LocalDate.now().getYear())) : new PageImpl<>(yers);
    }

    public Integer findCurrentMonth() {
        return existsDebitBalanceInCurrenthDate() ? LocalDate.now().getMonthValue() : LocalDate.now().plusMonths(1).getMonthValue();
    }

    public Integer findCurrentYear() {
        return existsDebitBalanceInCurrenthDate() ? LocalDate.now().getYear() : LocalDate.now().plusMonths(1).getYear();
    }

    private boolean existsDebitBalanceInCurrenthDate() {
        var firstMonth = LocalDate.now().withDayOfMonth(1);
        var lastMonth = LocalDate.now().withDayOfMonth(firstMonth.lengthOfMonth());
        var totalPaid = recordDebtorRepository.findTotalPaidByMonth(firstMonth, lastMonth, authenticationService.getUser().getAccount());
        var totalDebtor = recordDebtorRepository
                .findTotalByMonth(firstMonth, lastMonth, authenticationService.getUser().getAccount());

        return totalDebtor.orElse(BigDecimal.ZERO).subtract(totalPaid.orElse(BigDecimal.ZERO)).compareTo(BigDecimal.ZERO) > 0;
    }
}
