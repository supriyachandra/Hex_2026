package com.project.amazecare.service;

import com.project.amazecare.dto.TestReqDto;
import com.project.amazecare.mapper.TestsMapper;
import com.project.amazecare.model.Consultation;
import com.project.amazecare.model.RecommendedTests;
import com.project.amazecare.repository.TestsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TestsService {
    private final TestsRepository testsRepository;
    private final ConsultationService consultationService;

    public void addTest(TestReqDto testReqDto) {
        log.atLevel(Level.INFO).log("Called addTest: Add Tests by Consultation ID");

        // validate Consultation ID
        Consultation consultation= consultationService.findConsultById(testReqDto.consultation_id());

        RecommendedTests test= TestsMapper.mapToEntity(testReqDto);
        test.setConsultation(consultation);

        testsRepository.save(test);

        log.atLevel(Level.INFO).log("Tests added!");
    }
}
