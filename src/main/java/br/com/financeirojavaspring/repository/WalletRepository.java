package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.Account;
import br.com.financeirojavaspring.model.Wallet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

  Wallet findByUuid(String uuid);
   List<Wallet> findAllByAccount(Account account);
}
