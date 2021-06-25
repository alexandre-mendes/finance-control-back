package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.Record;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

  List<Record> findAllByWalletUuid(String uuid);
}
