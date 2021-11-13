package br.com.financecontrol.repository;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.RecordDebtor;
import br.com.financecontrol.entity.Wallet;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class RecordDebtorCriteriaRepository {

    private final EntityManager entityManager;

    public RecordDebtorCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<BigDecimal> findTotalPaid(final LocalDate firstDate, final LocalDate lastDate, final Account account) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        final Root<RecordDebtor> root = query.from(RecordDebtor.class);
        final Join<RecordDebtor, Wallet> walletJoin = root.join("wallet");

        query.select(criteriaBuilder.sum(root.get("value")))
                .where(criteriaBuilder.and(
                        criteriaBuilder.between(root.get("dateDeadline"), firstDate, lastDate),
                        criteriaBuilder.equal(root.get("paid"), true),
                        criteriaBuilder.equal(walletJoin.get("account"), account)));

        final BigDecimal totalPaid = entityManager.createQuery(query).getSingleResult();
        return Optional.ofNullable(totalPaid);
    }

    public Optional<BigDecimal> findTotal(final LocalDate firstDate, final LocalDate lastDate, final Account account) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        final Root<RecordDebtor> root = query.from(RecordDebtor.class);
        final Join<RecordDebtor, Wallet> walletJoin = root.join("wallet");

        query.select(criteriaBuilder.sum(root.get("value")))
                .where(criteriaBuilder.and(
                        criteriaBuilder.between(root.get("dateDeadline"), firstDate, lastDate),
                        criteriaBuilder.equal(walletJoin.get("account"), account)));

        final BigDecimal total = entityManager.createQuery(query).getSingleResult();
        return Optional.ofNullable(total);
    }

    public List<Integer> findAllYears(final Pageable pageable) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        final Root<RecordDebtor> root = query.from(RecordDebtor.class);

        query.select(obterApenasAno(criteriaBuilder, root, "dateDeadline")).distinct(true);

        return entityManager.createQuery(query)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    private Expression<Integer> obterApenasAno(final CriteriaBuilder criteriaBuilder, final Root root, final String campoData) {
        final Expression<String> obterMesNaData = criteriaBuilder.function(
                "to_char",
                String.class,
                root.get(campoData),
                criteriaBuilder.literal("YYYY"));

        return criteriaBuilder.function("to_number", Integer.class, obterMesNaData, criteriaBuilder.literal("9999"));
    }
}
