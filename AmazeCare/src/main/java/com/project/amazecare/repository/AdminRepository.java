package com.project.amazecare.repository;

import com.project.amazecare.dto.AdminRespDto;
import com.project.amazecare.model.Admin;
import com.project.amazecare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("select a.id, a.name from Admin a where a.user.username=?1")
    AdminRespDto getAdmin(String username);
}
