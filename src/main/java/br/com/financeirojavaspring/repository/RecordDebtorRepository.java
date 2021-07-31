package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.Account;
import br.com.financeirojavaspring.entity.RecordDebtor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
  Optional<BigDecimal> findTotalByMonth(
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
  Optional<BigDecimal> findTotalPaidByMonth(
      final @Param(value = "firstDate") LocalDate firstDate,
      final @Param(value = "lastDate") LocalDate lastDate,
      final @Param(value = "account") Account account
  );

  @Query(value =
        "select distinct "
      + "   extract(year from record.date_deadline) as yearRecord "
      + "FROM "
      + "   financeiro_record_debtor record", nativeQuery = true)
  List<Integer> findAllYears(final Pageable pageable);
}
