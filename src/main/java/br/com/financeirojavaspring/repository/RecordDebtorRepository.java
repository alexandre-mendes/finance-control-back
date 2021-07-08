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

public interface RecordRepository extends JpaRepository<RecordDebtor, Long> {

  Page<RecordDebtor> findAllfindAllByWalletUuidAndDeadlineBetween(String uuidWallet,
      LocalDate firstMonth,
      LocalDate lastMonth,
      Pageable pageable);

  @Query("select "
      + "   SUM(r.value) "
      + "from "
      + "   Record r "
      + "where "
      + "   r.wallet.typeWallet = :typeWallet "
      + "and "
      + "   r.deadline between :firstDate and :lastDate")
  BigDecimal findTotalByTypeWalletAndMonth(
      @Param(value = "typeWallet") TypeWallet typeWallet,
      @Param(value = "firstDate") LocalDate firstDate,
      @Param(value = "lastDate") LocalDate lastDate
  );

  @Query("select "
      + "   SUM(r.value) "
      + "from "
      + "   Record r "
      + "where "
      + "   r.wallet.typeWallet = 'DEBTOR' "
      + "and "
      + "   r.paid = true "
      + "and "
      + "   r.deadline between :firstDate and :lastDate")
  BigDecimal findTotalPaidByMonth(
      @Param(value = "firstDate") LocalDate firstDate,
      @Param(value = "lastDate") LocalDate lastDate
  );
}
