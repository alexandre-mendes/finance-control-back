package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.model.RecordDebtor;
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
      + "   r.dateDeadline between :firstDate and :lastDate")
  BigDecimal findTotalByTypeWalletAndMonth(
      @Param(value = "firstDate") LocalDate firstDate,
      @Param(value = "lastDate") LocalDate lastDate
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
      + "   r.dateDeadline between :firstDate and :lastDate")
  BigDecimal findTotalPaidByMonth(
      @Param(value = "firstDate") LocalDate firstDate,
      @Param(value = "lastDate") LocalDate lastDate
  );
}
