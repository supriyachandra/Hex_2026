package com.project.amazecare.repository;

import com.project.amazecare.model.Admin;
import com.project.amazecare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
