package org.gym.mapper;

import org.gym.dto.TrainingTypeDto;
import org.gym.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTypeMapperTest {

    private TrainingTypeMapper trainingTypeMapper = new TrainingTypeMapperImpl();

    @Test
    void convertToDto() {

        TrainingType trainingType = TrainingType.builder()
                .trainingTypeName("Yoga")
                .build();

        TrainingTypeDto trainingTypeDto = trainingTypeMapper.convertToDto(trainingType);

        assertNotNull(trainingTypeDto);
        assertEquals(trainingType.getTrainingTypeName(), trainingTypeDto.getTrainingTypeName());
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TrainingTypeDto trainingTypeDto = trainingTypeMapper.convertToDto(null);
        assertNull(trainingTypeDto, "ConvertToDto: null when input is null");
    }

    @Test
    void convertToEntity() {
        TrainingTypeDto trainingTypeDto = TrainingTypeDto.builder()
                .trainingTypeName("Yoga")
                .build();

        TrainingType trainingType = trainingTypeMapper.convertToEntity(trainingTypeDto);

        assertNotNull(trainingType);
        assertEquals(trainingTypeDto.getTrainingTypeName(), trainingType.getTrainingTypeName());
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        TrainingType trainingType = trainingTypeMapper.convertToEntity(null);
        assertNull(trainingType, "ConvertToEntity: null when input is null");
    }
}
