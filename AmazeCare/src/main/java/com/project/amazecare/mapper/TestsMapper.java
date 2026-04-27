package com.project.amazecare.mapper;

import com.project.amazecare.dto.TestDto;
import com.project.amazecare.dto.TestReqDto;
import com.project.amazecare.model.RecommendedTests;

public class TestsMapper {
    public static RecommendedTests mapToEntity(TestReqDto testReqDto){
        RecommendedTests test= new RecommendedTests();
        test.setTestName(testReqDto.test_name());
        return test;
    }

    public static TestDto mapToDto(RecommendedTests t){
        return new TestDto(
                t.getTestName()
        );
    }
}
