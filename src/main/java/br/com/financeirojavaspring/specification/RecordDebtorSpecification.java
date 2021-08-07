package br.com.financeirojavaspring.specification;

import br.com.financeirojavaspring.entity.RecordDebtor;
import br.com.financeirojavaspring.entity.Wallet;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class RecordDebtorSpecification implements Specification<RecordDebtor> {

    private static final long serialVersionUID = -6430654961954898653L;

    private final String uuidWalletDebtor;
    private final Boolean paid;
    private final LocalDate firstDate;
    private final LocalDate lastDate;

    public RecordDebtorSpecification(String uuidWalletDebtor, Boolean paid, LocalDate firstDate, LocalDate lastDate) {
        this.uuidWalletDebtor = uuidWalletDebtor;
        this.paid = paid;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }

    @Override
    public Predicate toPredicate(
            @NotNull Root<RecordDebtor> root,
            @NotNull CriteriaQuery<?> query,
            @NotNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Join<RecordDebtor, Wallet> join = root.join("wallet");

        if (nonNull(uuidWalletDebtor)) {
            predicates.add(criteriaBuilder.equal(join.get("uuid"), uuidWalletDebtor));
        }

        if (nonNull(paid)) {
            predicates.add(criteriaBuilder.equal(root.get("paid"), paid));
        }

        if (nonNull(firstDate) && nonNull(lastDate)) {
            predicates.add(criteriaBuilder.between(root.get("dateDeadline"), firstDate, lastDate));
        }

        return andToGether(criteriaBuilder, predicates);
    }

    private Predicate andToGether(CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
