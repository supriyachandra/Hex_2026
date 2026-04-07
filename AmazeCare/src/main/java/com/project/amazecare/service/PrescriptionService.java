package com.project.amazecare.service;

import com.project.amazecare.dto.PrescribeDto;
import com.project.amazecare.mapper.PrescriptionMapper;
import com.project.amazecare.model.Consultation;
import com.project.amazecare.model.Prescription;
import com.project.amazecare.repository.PrescriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final ConsultationService consultationService;

    public void prescribeMedicine(PrescribeDto prescribeDto) {
        log.atLevel(Level.INFO)
                .log("Called prescribeMedicine: Doctor prescribes medicine by consultation_id");

        Consultation consultation= consultationService
                .findConsultById(prescribeDto.consultation_id());
        Prescription prescription= PrescriptionMapper.mapToEntity(prescribeDto);
        prescription.setConsultation(consultation);
        prescriptionRepository.save(prescription);

        log.atLevel(Level.INFO).log("Prescription Added!");
    }
}
