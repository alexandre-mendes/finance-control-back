package br.com.financecontrol.repository;

import br.com.financecontrol.entity.RecordCreditor;
import br.com.financecontrol.entity.Wallet;
import br.com.financecontrol.projection.WalletProjection;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class WalletCriteriaRepository {

    private final EntityManager entityManager;

    public WalletCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<WalletProjection> findWalletCreditorWithTotal(final String id) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<WalletProjection> query = criteriaBuilder.createQuery(WalletProjection.class);
        final Root<Wallet> root = query.from(Wallet.class);
        final Join<Wallet, RecordCreditor> joinCreditor = root.join("recordsCreditor", JoinType.LEFT);

        query.select(criteriaBuilder.construct(WalletProjection.class,
                root.get("id"),
                root.get("title"),
                root.get("typeWallet"),
                root.get("dayWallet"),
                criteriaBuilder.sum(
                        criteriaBuilder.coalesce(joinCreditor.get("value"),
                        criteriaBuilder.literal(BigDecimal.ZERO))
                ),
                criteriaBuilder.literal(BigDecimal.ZERO)));

        query.where(criteriaBuilder.equal(root.get("id"), id));

        query.groupBy(root.get("id"));

        final WalletProjection wallet = entityManager.createQuery(query).getSingleResult();

        return Optional.ofNullable(wallet);
    }
}
