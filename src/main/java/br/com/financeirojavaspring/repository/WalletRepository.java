package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet> {

  Optional<Wallet> findByUuid(final String uuid);
}
