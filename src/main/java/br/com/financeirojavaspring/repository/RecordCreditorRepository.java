package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.RecordCreditor;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordCreditorRepository extends JpaRepository<RecordCreditor, Long> {

  Page<RecordCreditor> findAllfindAllByWalletUuidAndDateReceivementBetween(String uuidWallet,
      LocalDate firstMonth,
      LocalDate lastMonth,
      Pageable pageable);
}
