package br.com.financeirojavaspring.specification;

import br.com.financeirojavaspring.entity.Account;
import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.RecordDebtor;
import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.enums.TypeWallet;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.financeirojavaspring.enums.TypeWallet.CREDITOR;
import static br.com.financeirojavaspring.enums.TypeWallet.DEBTOR;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@lombok.AllArgsConstructor
public class WalletSpecification implements Specification<Wallet> {

    private static final long serialVersionUID = -4819186504454648097L;

    private Account account;
    private TypeWallet typeWallet;

    @Override
    public Predicate toPredicate(@NotNull Root<Wallet> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Join<Wallet, RecordCreditor> creditorJoin = root.join("recordsCreditor", JoinType.LEFT);
        Join<Wallet, RecordDebtor> debtorJoin = root.join("recordsDebtor", JoinType.LEFT);

        predicates.add(criteriaBuilder.equal(root.get("account"), account));

        query.distinct(true);

        if (nonNull(typeWallet)) {
            predicates.add(criteriaBuilder.equal(root.get("typeWallet"), typeWallet));
        }



        return andToGether(criteriaBuilder, predicates);
    }

    private Predicate andToGether(CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
