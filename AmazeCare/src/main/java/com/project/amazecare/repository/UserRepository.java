package com.project.amazecare.repository;

import com.project.amazecare.model.Specialization;
import com.project.amazecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username=?1")
    User getUserByUsername(String username);

    @Query("select s from Specialization s")
    List<Specialization> getAll();

    @Query("select u from User u where u.username=?1")
    User getRole(String username);
}
