package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.Account;
import br.com.financeirojavaspring.model.RecordCreditor;
import br.com.financeirojavaspring.model.Wallet;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordCreditorRepository extends JpaRepository<RecordCreditor, Long> {

  Page<RecordCreditor> findAllfindAllByWalletUuidAndDateReceivementBetween(String uuidWallet,
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
      + "   r.dateReceivement between :firstDate and :lastDate")
  Optional<BigDecimal> findTotalByTypeWalletAndMonth(
      @Param(value = "firstDate") LocalDate firstDate,
      @Param(value = "lastDate") LocalDate lastDate,
      @Param(value = "account") Account account
  );
}
