package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.model.RecordCreditor;
import java.math.BigDecimal;
import java.time.LocalDate;
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
      + "   r.dateReceivement between :firstDate and :lastDate")
  BigDecimal findTotalByTypeWalletAndMonth(
      @Param(value = "firstDate") LocalDate firstDate,
      @Param(value = "lastDate") LocalDate lastDate
  );
}
