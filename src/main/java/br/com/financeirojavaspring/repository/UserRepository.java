package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);

    User findByUuid(String uuidUser);
}
