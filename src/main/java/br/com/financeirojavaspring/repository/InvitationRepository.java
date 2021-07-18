package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
