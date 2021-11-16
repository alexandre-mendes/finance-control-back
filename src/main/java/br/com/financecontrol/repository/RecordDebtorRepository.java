package br.com.financecontrol.repository;

import br.com.financecontrol.entity.RecordDebtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordDebtorRepository extends JpaRepository<RecordDebtor, String>, JpaSpecificationExecutor<RecordDebtor> {}
