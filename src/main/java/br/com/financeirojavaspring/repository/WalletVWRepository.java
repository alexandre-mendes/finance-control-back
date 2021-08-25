package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.WalletVW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WalletVWRepository extends JpaRepository<WalletVW, WalletVW.ID>, JpaSpecificationExecutor<WalletVW> {
}
