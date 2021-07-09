package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
