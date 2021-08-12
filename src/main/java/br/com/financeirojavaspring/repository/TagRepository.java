package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
