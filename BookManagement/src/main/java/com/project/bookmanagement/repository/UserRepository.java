package com.project.bookmanagement.repository;

import com.project.bookmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username=?1")
    User getUserByUsername(String username);
}
