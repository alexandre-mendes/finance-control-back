package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.Account;
import br.com.financeirojavaspring.entity.RecordCreditor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordCreditorRepository extends JpaRepository<RecordCreditor, Long>, JpaSpecificationExecutor<RecordCreditor> {

  Page<RecordCreditor> findAllfindAllByWalletUuidAndDateTransactionBetween(String uuidWallet,
      LocalDate firstMonth,
      LocalDate lastMonth,
      Pageable pageable);

  @Query("select "
      + "   SUM(r.value) "
      + "from "
      + "   RecordCreditor r "
      + "where "
      + "   r.wallet.typeWallet = 'CREDITOR' "
      + "and "
      + "   r.wallet.account = :account "
      + "and "
      + "   r.dateTransaction <= now()")
  Optional<BigDecimal> findTotalByTypeWalletAndMonth(
      @Param(value = "account") final Account account
  );
}
