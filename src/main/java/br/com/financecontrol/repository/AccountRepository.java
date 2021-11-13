package br.com.financecontrol.repository;

import br.com.financecontrol.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
