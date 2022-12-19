package com.keremcengiz0.questapp.repository.abstracts;

import com.keremcengiz0.questapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
