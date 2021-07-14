package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.dto.WalletDTO;
import br.com.financeirojavaspring.enums.TypeWallet;
import br.com.financeirojavaspring.model.Account;
import br.com.financeirojavaspring.model.Wallet;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
