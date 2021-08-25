package br.com.financeirojavaspring.specification;

import br.com.financeirojavaspring.entity.WalletVW;
import br.com.financeirojavaspring.enums.TypeWallet;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.financeirojavaspring.enums.Month.valueToEnum;
import static br.com.financeirojavaspring.enums.TypeWallet.DEBTOR;
import static java.util.Objects.nonNull;

@lombok.AllArgsConstructor
public class WalletVWSpecification implements Specification<WalletVW> {

    private static final long serialVersionUID = -4819186504454648097L;

    private Long idAccount;
    private LocalDate firstDate;
    private LocalDate lastDate;
    private TypeWallet typeWallet;

    @Override
    public Predicate toPredicate(@NotNull Root<WalletVW> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(root.get("idAccount"), idAccount));

        if (nonNull(typeWallet)) {
            predicates.add(criteriaBuilder.equal(root.get("typeWallet"), typeWallet));
        }

        if (DEBTOR.equals(typeWallet) && nonNull(firstDate)) {
            predicates.add(criteriaBuilder.equal(root.get("monthWallet"), valueToEnum(firstDate.getMonthValue())));
        }

        return andToGether(criteriaBuilder, predicates);
    }

    private Predicate andToGether(CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
