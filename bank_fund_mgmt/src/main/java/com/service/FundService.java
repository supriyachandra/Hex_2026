package com.service;

import com.model.Fund;
import com.model.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FundService {


    @PersistenceContext     // activates EntityManager of Hibernate/JPA
    private EntityManager em;


    @Transactional
    public void addManager(Manager manager) {
        em.persist(manager);
    }

    @Transactional
    public void addFund(Fund fund, Long managerId) {

        Manager manager= em.find(Manager.class, managerId);
        if(manager==null){
            throw new RuntimeException("Manager ID not found");
        }

        fund.setManager(manager);
        fund.setCreatedAt(Instant.now());

        em.persist(fund);

    }

    public List<Fund> getFundsByManager(Long mid) {
        String jpql= "select f from Fund f where f.manager.id= :mid";
        Query query= em.createQuery(jpql, Fund.class);
        query.setParameter("mid", mid);

        return query.getResultList();

    }
}
