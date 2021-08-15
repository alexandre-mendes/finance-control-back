package br.com.financeirojavaspring.specification;

import static java.util.Objects.nonNull;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.Wallet;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@lombok.AllArgsConstructor
public class RecordCreditorSpecification implements Specification<RecordCreditor> {

    private static final long serialVersionUID = 5322484802166887178L;

    private String uuidWallet;
    private LocalDate firstDate;
    private LocalDate lastDate;

    @Override
    public Predicate toPredicate(@NonNull Root<RecordCreditor> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {

        final Join<RecordCreditor, Wallet> wallet = root.join("wallet");
        final List<Predicate> predicates = new ArrayList<>();
        query.orderBy(criteriaBuilder.desc(root.get("id")));

        if (nonNull(uuidWallet)) {
            predicates.add(criteriaBuilder.equal(wallet.get("uuid"), uuidWallet));
        }

        if (nonNull(firstDate) && nonNull(lastDate)) {
            predicates.add(criteriaBuilder.between(root.get("dateTransaction"), firstDate, lastDate));
        }

        return andToGether(criteriaBuilder, predicates);
    }

    private Predicate andToGether(CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
