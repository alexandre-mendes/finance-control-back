package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
