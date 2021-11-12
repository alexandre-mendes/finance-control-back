package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.Wallet;
import br.com.financeirojavaspring.projection.VWWalletCreditorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet> {

  @Query(value = "select * from vw_wallet_creditor where uuid = :uuid", nativeQuery = true)
  Optional<VWWalletCreditorProjection> findWalletCreditorProjection(@Param(value = "uuid") final String uuid);

  Optional<Wallet> findByUuid(final String uuid);
}
