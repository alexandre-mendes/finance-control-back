package br.com.financecontrol.repository;

import br.com.financecontrol.entity.ProofPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProofPaymentRepository extends JpaRepository<ProofPayment, String> {
}
