package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
