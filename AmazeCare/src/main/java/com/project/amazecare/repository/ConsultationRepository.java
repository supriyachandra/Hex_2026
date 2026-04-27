package com.project.amazecare.repository;

import com.project.amazecare.enums.PatientType;
import com.project.amazecare.model.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query("""
    select c from Consultation c
    left join c.appointment a
    left join a.patient ap
    left join ap.user au
    left join c.admission ad
    left join ad.patient dp
    left join dp.user du
    where (au.username = ?1)
    or (du.username = ?1)
        order by a.appointmentDate desc
    """)
    List<Consultation> getAllByUsername(String username);

    @Query("""
    select count(c) from Consultation c
    left join c.appointment a
    left join a.patient ap
    left join ap.user au
    left join c.admission ad
    left join ad.patient dp
    left join dp.user du
    where (au.username = ?1)
    or (du.username = ?1)
    """)
    int countVisitsByUsername(String username);

    @Query("""
select c from Consultation c
join c.admission ad
join ad.doctor dp
join dp.user du
where du.username = ?1
and c.appointment is null
order by ad.admissionDate desc
""")
    Page<Consultation> getIpdConsults(
            String username,
            Pageable pageable
    );

    @Query("""
select c from Consultation c
join c.appointment a
join a.doctor ap
join ap.user au
where au.username = ?1
and c.admission is null
order by a.appointmentDate desc
""")
    Page<Consultation> getOpdConsults(
            String username,
            Pageable pageable
    );

    @Query("""
    select c from Consultation c where c.appointment.id=?1
        and c.appointment.patient.user.username=?2
    """)
    Consultation getConsultByAppId(Long appId, String username);
}
