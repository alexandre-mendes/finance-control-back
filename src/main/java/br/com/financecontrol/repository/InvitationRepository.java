package br.com.financecontrol.repository;

import br.com.financecontrol.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
}
