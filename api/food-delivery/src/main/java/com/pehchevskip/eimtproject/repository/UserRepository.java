package com.pehchevskip.eimtproject.repository;

import com.pehchevskip.eimtproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Transactional
    long deleteByUsername(String username); // returns number of deleted users
}
