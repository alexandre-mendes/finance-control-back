package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUuid(String uuid);
}
