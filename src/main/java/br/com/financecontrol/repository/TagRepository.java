package br.com.financecontrol.repository;

import br.com.financecontrol.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, String> {

    Optional<Tag> findByUuid(final String uuid);
}
