package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.RecordDebtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordDebtorRepository extends JpaRepository<RecordDebtor, Long>, JpaSpecificationExecutor<RecordDebtor> {}
