package com.project.amazecare.service;

import com.project.amazecare.model.Specialization;
import com.project.amazecare.repository.SpecializationRepository;
import com.project.amazecare.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpecializationService {
    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;

    public List<Specialization> getAllSpecialization() {
        return userRepository.getAll();
    }


    public void add(String specialization) {
        Specialization s= new Specialization();
        s.setName(specialization);
        specializationRepository.save(s);
    }
}
