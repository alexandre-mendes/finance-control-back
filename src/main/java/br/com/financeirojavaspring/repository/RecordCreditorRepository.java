package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.RecordCreditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecordCreditorRepository extends JpaRepository<RecordCreditor, Long>, JpaSpecificationExecutor<RecordCreditor> {}
