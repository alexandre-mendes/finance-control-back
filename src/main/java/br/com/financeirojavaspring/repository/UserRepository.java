package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUuid(String uuid);
    Optional<User> findByUsername(String username);
}
