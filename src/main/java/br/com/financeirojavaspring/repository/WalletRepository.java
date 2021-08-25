package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.projection.VWWalletCreditorProjection;
import br.com.financeirojavaspring.projection.WalletProjection;
import br.com.financeirojavaspring.entity.Wallet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

  @Query(value = "select * from vw_wallet_creditor where uuid = :uuid", nativeQuery = true)
  Optional<VWWalletCreditorProjection> findByUUID(@Param(value = "uuid") final String uuid);
}
