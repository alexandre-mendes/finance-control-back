package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.Invitation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
  Optional<Invitation> findByUuid(String uuid);
}
