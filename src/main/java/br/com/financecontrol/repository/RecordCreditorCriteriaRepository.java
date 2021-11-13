package br.com.financecontrol.repository;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.Wallet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public class RecordCreditorCriteriaRepository {

    private final EntityManager entityManager;

    public RecordCreditorCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<BigDecimal> findTotal(final Account account) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        final Root<RecordCreditor> root = query.from(RecordCreditor.class);
        final Join<RecordCreditor, Wallet> walletJoin = root.join("wallet");
        final LocalDate date = LocalDate.now();

        query.select(criteriaBuilder.sum(root.get("value")))
                .where(criteriaBuilder.and(
                        criteriaBuilder.or(
                                criteriaBuilder.lessThan(root.get("dateTransaction"), date),
                                criteriaBuilder.equal(root.get("dateTransaction"), date)),
                        criteriaBuilder.equal(walletJoin.get("account"), account)));

        final BigDecimal total = entityManager.createQuery(query).getSingleResult();
        return Optional.ofNullable(total);
    }
}
