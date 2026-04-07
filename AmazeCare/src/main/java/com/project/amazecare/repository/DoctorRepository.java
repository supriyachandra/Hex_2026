package com.project.amazecare.repository;

import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
            select d from Doctor d where d.specialization.id= ?1
            """)
    public List<Doctor> filterDoctorBySpecializationId(long specialization_id);

    List<Doctor> user(User user);

    @Query("select d from Doctor d where d.user.username=?1")
    Doctor getByUsername(String username);
}
