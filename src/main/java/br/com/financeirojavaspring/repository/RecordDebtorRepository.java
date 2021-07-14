package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.Account;
import br.com.financeirojavaspring.model.RecordDebtor;
import br.com.financeirojavaspring.model.Wallet;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordDebtorRepository extends JpaRepository<RecordDebtor, Long> {

  Page<RecordDebtor> findAllfindAllByWalletUuidAndDateDeadlineBetween(String uuidWallet,
      LocalDate firstMonth,
      LocalDate lastMonth,
      Pageable pageable);

  @Query("select "
      + "   SUM(r.value) "
      + "from "
      + "   RecordDebtor r "
      + "where "
      + "   r.wallet.typeWallet = 'DEBTOR' "
      + "and "
      + "   r.wallet.account = :account "
      + "and "
      + "   r.dateDeadline between :firstDate and :lastDate")
  BigDecimal findTotalByMonth(
      final @Param(value = "firstDate") LocalDate firstDate,
      final @Param(value = "lastDate") LocalDate lastDate,
      final @Param(value = "account") Account account
  );

  @Query("select "
      + "   SUM(r.value) "
      + "from "
      + "   RecordDebtor r "
      + "where "
      + "   r.wallet.typeWallet = 'DEBTOR' "
      + "and "
      + "   r.paid = true "
      + "and "
      + "   r.wallet.account = :account "
      + "and "
      + "   r.dateDeadline between :firstDate and :lastDate")
  BigDecimal findTotalPaidByMonth(
      final @Param(value = "firstDate") LocalDate firstDate,
      final @Param(value = "lastDate") LocalDate lastDate,
      final @Param(value = "account") Account account
  );

  @Query("select "
      + "   SUM(r.value) "
      + "from "
      + "   RecordDebtor r "
      + "where "
      + "   r.wallet = :wallet "
      + "and "
      + "   r.dateDeadline between :firstDate and :lastDate")
  BigDecimal findTotalByWalletAndMonth(
      final @Param(value = "wallet") Wallet wallet,
      final @Param(value = "firstDate") LocalDate firstDate,
      final @Param(value = "lastDate") LocalDate lastDate);
}
