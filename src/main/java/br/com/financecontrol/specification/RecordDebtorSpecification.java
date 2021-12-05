package br.com.financecontrol.specification;

import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.entity.Wallet;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static br.com.financecontrol.enums.TypeDay.FIRST_DAY_MONTH;
import static br.com.financecontrol.enums.TypeDay.LAST_DAY_MONTH;
import static br.com.financecontrol.util.DateCreator.createLocalDate;
import static java.util.Objects.nonNull;

@lombok.Builder
public class RecordDebtorSpecification implements Specification<RecordDebtor> {

    private static final long serialVersionUID = -6430654961954898653L;

    private String walletId;
    private Boolean paid;
    private Integer month;
    private Integer year;

    @Override
    public Predicate toPredicate(
            @NotNull Root<RecordDebtor> root,
            @NotNull CriteriaQuery<?> query,
            @NotNull CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        final Join<RecordDebtor, Wallet> join = root.join("wallet");
        query.orderBy(criteriaBuilder.desc(root.get("createDate")));

        if (nonNull(walletId)) {
            predicates.add(criteriaBuilder.equal(join.get("id"), walletId));
        }

        if (nonNull(paid)) {
            predicates.add(criteriaBuilder.equal(root.get("paid"), paid));
        }

        if (nonNull(month) && nonNull(year)) {
            predicates.add(
                    criteriaBuilder.between(
                            root.get("dateDeadline"),
                            createLocalDate(month, year, FIRST_DAY_MONTH),
                            createLocalDate(month, year, LAST_DAY_MONTH)));
        }

        return andToGether(criteriaBuilder, predicates);
    }

    private Predicate andToGether(CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
