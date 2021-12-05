package br.com.financecontrol.specification;

import static br.com.financecontrol.util.DateCreator.createLocalDate;
import static java.util.Objects.nonNull;
import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.Wallet;
import br.com.financecontrol.enums.TypeDay;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@lombok.AllArgsConstructor
public class RecordCreditorSpecification implements Specification<RecordCreditor> {

    private static final long serialVersionUID = 5322484802166887178L;

    private String walletId;
    private Integer month;
    private Integer year;

    @Override
    public Predicate toPredicate(@NonNull Root<RecordCreditor> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {

        final Join<RecordCreditor, Wallet> wallet = root.join("wallet");
        final List<Predicate> predicates = new ArrayList<>();
        query.orderBy(criteriaBuilder.desc(root.get("createDate")));

        if (nonNull(walletId)) {
            predicates.add(criteriaBuilder.equal(wallet.get("id"), walletId));
        }

        if (nonNull(month) && nonNull(year)) {
            predicates.add(criteriaBuilder.between(root.get("dateTransaction"),
                    createLocalDate(month, year, TypeDay.FIRST_DAY_MONTH),
                    createLocalDate(month, year, TypeDay.LAST_DAY_MONTH)));
        }

        return andToGether(criteriaBuilder, predicates);
    }

    private Predicate andToGether(CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
