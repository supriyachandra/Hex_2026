package com.project.amazecare.repository;

import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
            select d from Doctor d where d.specialization.id= ?1
            """)
    public List<Doctor> filterDoctorBySpecializationId(long specialization_id);

    @Query("select d from Doctor d where d.user.username=?1")
    Doctor getByUsername(String username);

    @Query("""
            select d from Doctor d
            where (?1 is null or lower(d.name) like lower(concat('%',?1, '%')))
            and (?2 is null or d.specialization.id=?2)
            """)
    List<Doctor> filterBySpecAndName(String name, Long specId);

    @Query("""
        select d from Doctor d
        join DoctorSchedule ds
        on d.id = ds.doctor.id
        where ds.date= ?1
        """)
    List<Doctor> doctorByDate(LocalDate date);

    @Query("select count(d.id) from Doctor d")
    Long totalDoctors();

//    @Query("""
//        select c from Doctor d
//                inner join Consultation c
//        """)
//    void get(String username);
}
