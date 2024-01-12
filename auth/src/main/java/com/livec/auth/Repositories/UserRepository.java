package com.livec.auth.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livec.auth.models.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    
    List<User> findAll();

    Optional<User> findByUsername(String username);

    List<User> findAllByIsNonLocked(boolean nonLocked);

}
