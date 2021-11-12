package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.Account;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.RecordDebtor;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.projection.WalletProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Repository
public class WalletCriteriaRepository {

    private final EntityManager entityManager;

    public WalletCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<WalletProjection> findAll(final Account account,
                                          final TypeWallet typeWallet,
                                          final LocalDate firstDate,
                                          final LocalDate lastDate,
                                          final Pageable pageable) {
        final List<Predicate> predicates = new ArrayList<>();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<WalletProjection> query = criteriaBuilder.createQuery(WalletProjection.class);
        final Root<Wallet> root = query.from(Wallet.class);
        final Join<Wallet, RecordCreditor> joinCreditor = root.join("recordsCreditor", JoinType.LEFT);
        final Join<Wallet, RecordDebtor> joinDebtor = root.join("recordsDebtor", JoinType.LEFT);

        if (nonNull(firstDate) && nonNull(lastDate)) {
            joinCreditor.on(
                    criteriaBuilder.and(criteriaBuilder.equal(joinCreditor.get("wallet"), root),
                            criteriaBuilder.between(joinCreditor.get("dateTransaction"), firstDate, lastDate)));

            joinDebtor.on(criteriaBuilder.and(
                    criteriaBuilder.and(criteriaBuilder.equal(joinCreditor.get("wallet"), root),
                            criteriaBuilder.between(joinDebtor.get("dateDeadline"), firstDate, lastDate))));
        }

        query.select(criteriaBuilder.construct(WalletProjection.class,
                root.get("id"),
                root.get("uuid"),
                root.get("title"),
                root.get("typeWallet"),
                root.get("dayWallet"),
                criteriaBuilder.sum(
                        criteriaBuilder.coalesce(joinCreditor.get("value"), joinDebtor.get("value"))
                ),
                criteriaBuilder.sum(
                        criteriaBuilder.selectCase()
                                .when(criteriaBuilder.isTrue(joinDebtor.get("paid")), joinDebtor.get("value"))
                                .otherwise(BigDecimal.ZERO).as(BigDecimal.class)
                )));

        predicates.add(criteriaBuilder.equal(root.get("account"), account));

        if (nonNull(typeWallet)) predicates.add(criteriaBuilder.equal(root.get("typeWallet"), typeWallet));

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        query.groupBy(root.get("id"));

        final List<WalletProjection> wallets = entityManager.createQuery(query)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(pageable.getPageNumber())
                .getResultList();

        //TODO usar um count para adicionar o total
        return new PageImpl<>(wallets, pageable, 0);
    }

    public Optional<WalletProjection> findWalletCreditor(final String uuid) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<WalletProjection> query = criteriaBuilder.createQuery(WalletProjection.class);
        final Root<Wallet> root = query.from(Wallet.class);
        final Join<Wallet, RecordCreditor> joinCreditor = root.join("recordsCreditor", JoinType.LEFT);

        query.select(criteriaBuilder.construct(WalletProjection.class,
                root.get("id"),
                root.get("uuid"),
                root.get("title"),
                root.get("typeWallet"),
                root.get("dayWallet"),
                criteriaBuilder.sum(
                        criteriaBuilder.coalesce(joinCreditor.get("value"),
                        criteriaBuilder.literal(BigDecimal.ZERO))
                ),
                criteriaBuilder.literal(BigDecimal.ZERO)));

        query.where(criteriaBuilder.equal(root.get("uuid"), uuid));

        query.groupBy(root.get("id"));

        final WalletProjection wallet = entityManager.createQuery(query).getSingleResult();

        return Optional.ofNullable(wallet);
    }
}
