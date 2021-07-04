package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.Record;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

  Page<Record> findAllfindAllByWalletUuidAndDeadlineBetween(String uuidWallet,
      LocalDate firstMonth,
      LocalDate lastMonth,
      Pageable pageable);
}
