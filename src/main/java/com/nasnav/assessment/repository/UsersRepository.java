package com.nasnav.assessment.repository;

import com.nasnav.assessment.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByEmail(String email);

  boolean existsByEmail(String email);
}
