package br.com.financecontrol.repository;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.Wallet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Repository
public class RecordCreditorCriteriaRepository {

    private final EntityManager entityManager;

    public RecordCreditorCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<BigDecimal> findTotal(final Account account, final String walletId) {
        final List<Predicate> predicates = new ArrayList<>();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        final Root<RecordCreditor> root = query.from(RecordCreditor.class);
        final Join<RecordCreditor, Wallet> walletJoin = root.join("wallet");
        final LocalDate date = LocalDate.now();

        predicates.add(criteriaBuilder.or(
                criteriaBuilder.lessThan(root.get("dateTransaction"), date),
                criteriaBuilder.equal(root.get("dateTransaction"), date)));

        if (isNull(walletId))
            predicates.add(criteriaBuilder.equal(walletJoin.get("account"), account));
        else
            predicates.add(criteriaBuilder.equal(walletJoin.get("id"), walletId));

        query.select(criteriaBuilder.sum(root.get("value")));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        final BigDecimal total = entityManager.createQuery(query).getSingleResult();
        return Optional.ofNullable(total);
    }
}
