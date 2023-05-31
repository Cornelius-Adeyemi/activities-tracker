package com.adebisi.task_eight.repo;


import com.adebisi.task_eight.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUsersByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmail(String email);

}
